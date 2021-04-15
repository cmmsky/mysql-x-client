package com.cmmsky.mysql.x.client.binlog;

import com.cmmsky.mysql.x.client.binlog.packet.Event;
import com.cmmsky.mysql.x.client.binlog.packet.EventHeader;
import com.cmmsky.mysql.x.client.binlog.packet.EventHeaderV4;
import com.cmmsky.mysql.x.client.core.protocol.packets.request.GtidSet;
import com.cmmsky.mysql.x.client.core.MySQLDataSource;
import com.cmmsky.mysql.x.client.core.executor.MysqlQueryExecutor;
import com.cmmsky.mysql.x.client.core.executor.MysqlUpdateExecutor;
import com.cmmsky.mysql.x.client.core.protocol.packets.*;
import com.cmmsky.mysql.x.client.core.protocol.packets.request.BinlogDumpCommandPacket;
import com.cmmsky.mysql.x.client.core.utils.ErrorPacketException;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

/**
 * @Author: cmmsky
 * @Date: Created in 17:27 2021/4/12
 * @Description:
 * @Modified by:
 */
public class BinlogDumpServer extends Thread {


    private String fileName;
    private long pos;
    private MySQLDataSource dataSource;
    private BinlogDumpContext context;
    private int checksumLength;
    private TimerTask heartBeatTimerTask;
    private Timer timer;
    private long lastHeartBeat = 0;
    private LogPosition logPosition;

    private boolean startAsc = false;

    public static final int       MASTER_HEARTBEAT_PERIOD_SECONDS = 15;
    public static final int       detectingIntervalInSeconds = 3;


    public BinlogDumpServer() {
        dataSource = new MySQLDataSource("127.0.0.1", 3306, "root", "123456");
        registerListener();

    }


    @Override
    public void run() {
        context = new BinlogDumpContext();
        BinlogDumpContext.setBinlogDumpContext(context);
        if (fileName == null) {
            getFileNameAndPos();
        }
        updateSettings();
        ChecksumType checksumType = null;
        try {
            checksumType = fetchBinlogChecksum();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            String gtidSet = fetchGtid();
            context.setGtidSet(new GtidSet(gtidSet));
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (checksumType != ChecksumType.NONE) {
            try {
                confirmSupportOfChecksum(checksumType);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        context.setChecksumType(checksumType);
        setHeartBeat();
        try {
            binlogDump();
        } catch (Exception e) {
            System.out.println(String.format("binlog 日志同步发生异常 %s", e.getMessage()));
        }

    }

    public String fetchGtid() throws Exception {
        MysqlQueryExecutor queryExecutor = getQueryExecutor();
        ResultSetPacket query = queryExecutor.query("show global variables like 'gtid_purged'");
        System.out.println(query.toString());
        if (query.getFieldValues() != null && query.getFieldValues().size() > 0) {
            return query.getFieldValues().get(1);
        }
        return "";
    }

    public void setHeartBeat() {

        if (timer == null) {
            timer = new Timer("heartBeat", true);
        }
        if (heartBeatTimerTask == null) {
            heartBeatTimerTask = buildHeartBeatTask();
            timer.schedule(heartBeatTimerTask, detectingIntervalInSeconds * 1000L, detectingIntervalInSeconds * 1000L);

        }
    }

    public TimerTask buildHeartBeatTask() {
        return new TimerTask() {
            @Override
            public void run() {
                if ((System.currentTimeMillis() - lastHeartBeat) / 1000 > MASTER_HEARTBEAT_PERIOD_SECONDS) {
                    System.out.println("master失去连接");
                } else {
                    System.out.println(String.format("{ heartBeart check time 上一次检查时间 %s 本次时间 %s }", lastHeartBeat, System.currentTimeMillis()));
                }
            }
        };
    }

    public void binlogDump() throws Exception {
        BinlogDumpContext binlogDumpContext = BinlogDumpContext.getBinlogDumpContext();
        GtidSet gtidSet = binlogDumpContext.getGtidSet();
        // if (gtidSet != null && gtidSet.getUUIDSets())
        BinlogDumpCommandPacket binlogDumpCommandPacket = new BinlogDumpCommandPacket();
        binlogDumpCommandPacket.setCommand(MySQLPacket.COM_BINLOG_DUMP);
        binlogDumpCommandPacket.setBinlogFileName(fileName);
        binlogDumpCommandPacket.setBinlogPosition(pos);
        binlogDumpCommandPacket.setPacketId((byte) 0);
        binlogDumpCommandPacket.setBinlogFlags(0);
        binlogDumpCommandPacket.setBinlogFlags(binlogDumpCommandPacket.getBinlogFlags() | BinlogDumpCommandPacket.BINLOG_SEND_ANNOTATE_ROWS_EVENT);
        // binlogDumpCommandPacket.binlogFlags |= binlogDumpCommandPacket.BINLOG_SEND_ANNOTATE_ROWS_EVENT;
        binlogDumpCommandPacket.setSlaveServerId(4);
        binlogDumpCommandPacket.write(dataSource.getConnection().getChannel());
        startAsc = true;
        while (startAsc) {
            BinaryPacket receive = receive();
            switch (receive.getBody()[0]) {
                case ErrorPacket.FIELD_COUNT:
                    ErrorPacketException.handleFailure(receive);
                    break;
                case EOFPacket.FIELD_COUNT:
                    return;
                default:
                    Event event = readEvent(receive);
                    EventHeaderV4 headerV4 = (EventHeaderV4)event.getEventHeader();
                    if ( headerV4 instanceof EventHeaderV4) {
                        logPosition.setPosition(headerV4.getNextLogPos());
                    }
                    System.out.println(logPosition.toString());
                    break;

            }
        }

    }

    public Event readEvent(BinaryPacket bin) {
        Event eventData = new Event();
        eventData.read(bin);
        System.out.println(eventData.toString());
        // 记录一下有数据包的时间
        lastHeartBeat = System.currentTimeMillis();
        return eventData;
    }

    private void confirmSupportOfChecksum(ChecksumType checksumType) throws Exception {
        update("set @master_binlog_checksum= @@global.binlog_checksum");
        checksumLength = checksumType.getLength();

    }

    public void getFileNameAndPos() {
        if (logPosition != null) {
            pos = logPosition.getPosition();
            fileName = logPosition.getJournalName();
            return;
        }
        try {
            ResultSetPacket result = getQueryExecutor().query("show master status");
            fileName = result.getFieldValues().get(0);
            pos = Long.parseLong(result.getFieldValues().get(1));
            System.out.println(String.format("获取到的binlog name 为 %s pos 为 %s", fileName, pos));
            logPosition = new LogPosition(fileName, pos, 0L);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateSettings() {
        try {
            update("set wait_timeout=9999999");
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            update("set net_write_timeout=1800");
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            update("set net_read_timeout=1800");
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 参考:https://github.com/alibaba/canal/issues/284
        // mysql5.6需要设置slave_uuid避免被server kill链接
        try {
            OkPacket update = update("set @slave_uuid=uuid()");
            // System.out.println(update.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            long periodNano = TimeUnit.SECONDS.toNanos(MASTER_HEARTBEAT_PERIOD_SECONDS);
            update("SET @master_heartbeat_period=" + periodNano);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private ChecksumType fetchBinlogChecksum() throws Exception {
        ResultSetPacket result = query("select @@global.binlog_checksum");
        List<String> fieldValues = result.getFieldValues();
        if (fieldValues.size() == 0)
            return ChecksumType.NONE;

        return ChecksumType.valueOf(fieldValues.get(0).toUpperCase());
    }

    public ResultSetPacket query(String sql) throws Exception {
        return dataSource.getConnection().getQueryExecutor().query(sql);
    }
    public OkPacket update(String sql) throws Exception {
        MysqlUpdateExecutor updateExecutor = dataSource.getConnection().getUpdateExecutor();
        return updateExecutor.update(sql);
    }

    public BinaryPacket receive() throws Exception {
        return getQueryExecutor().receive();
    }

    public MysqlQueryExecutor getQueryExecutor() throws Exception {
        if (dataSource == null) {
            throw new Exception("数据源为空");
        }
        return dataSource.getConnection().getQueryExecutor();
    }

    public void registerListener() {
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("shutdown ");
                startAsc = false;
            }
        }));
    }

}
