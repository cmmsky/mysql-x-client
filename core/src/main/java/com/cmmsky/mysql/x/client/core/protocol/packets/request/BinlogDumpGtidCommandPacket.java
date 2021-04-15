package com.cmmsky.mysql.x.client.core.protocol.packets.request;

import com.cmmsky.mysql.x.client.core.protocol.packets.MySQLPacket;
import com.cmmsky.mysql.x.client.core.protocol.socket.SocketChannel;
import com.cmmsky.mysql.x.client.core.utils.StreamUtil;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

/**
 * @Author: dongguapi
 * @Date: Created in 15:52 2021/4/15
 * @Description:
 * @Modified by:
 */
public class BinlogDumpGtidCommandPacket extends MySQLPacket {

    public static final int BINLOG_DUMP_NON_BLOCK           = 1;
    public static final int BINLOG_SEND_ANNOTATE_ROWS_EVENT = 2;

    // 二进制日志数据的启示位置
    private long   binlogPosition;
    // 二进制日志数据标志位
    private int    binlogFlags;
    // 从服务器的服务器ID值
    private int   slaveServerId;
    // 二进制文件名称
    private String binlogFileName;

    private GtidSet gtidSet;


    public void write(SocketChannel channel) throws IOException {
        StreamUtil.writeUB3(channel, getPacketLength());
        StreamUtil.write(channel, packetId);
        StreamUtil.write(channel, COM_BINLOG_DUMP);
        StreamUtil.writeUB2(channel, binlogFlags);
        StreamUtil.writeUB4(channel, slaveServerId);
        StreamUtil.writeWithLength(channel, binlogFileName == null ? new byte[0] : binlogFileName.getBytes());
        StreamUtil.writeLong(channel, binlogPosition);
        Collection<GtidSet.UUIDSet> uuidSets = gtidSet.getUUIDSets();
        int dataSize = 8;
        for (GtidSet.UUIDSet uuidSet : uuidSets) {
            dataSize += 16 + 8 + uuidSet.getIntervals().size() * 16;

        }

        StreamUtil.writeUB4(channel, dataSize);
        StreamUtil.writeLong(channel, uuidSets.size());
        for (GtidSet.UUIDSet uuidSet : uuidSets) {
            StreamUtil.writeBytes(channel, hexToByteArray(uuidSet.getUUID().replace("-", "")));
            List<GtidSet.Interval> intervals = uuidSet.getIntervals();

            StreamUtil.writeLong(channel, intervals.size());
            for (GtidSet.Interval interval : intervals) {
                StreamUtil.writeLong(channel, interval.getStart());
                StreamUtil.writeLong(channel, interval.getEnd() + 1);
            }
        }

    }

    protected int getPacketLength() {
        return 1 + 4 + 2 + 4 + (binlogFileName == null ? 0: binlogFileName.getBytes().length) + 8 + 4 + 8 + getGtidSetLength();
    }

    private int getGtidSetLength() {
        Collection<GtidSet.UUIDSet> uuidSets = gtidSet.getUUIDSets();
        int sum = 0;
        for (GtidSet.UUIDSet uuidSet : uuidSets) {
            sum += hexToByteArray(uuidSet.getUUID().replace("-", "")).length;
            sum += 8;
            Collection<GtidSet.Interval> intervals = uuidSet.getIntervals();
            for (GtidSet.Interval interval : intervals) {
                sum += 16;
            }
        }
        return sum;
    }

    private static byte[] hexToByteArray(String uuid) {
        byte[] b = new byte[uuid.length() / 2];
        for (int i = 0, j = 0; j < uuid.length(); j += 2) {
            b[i++] = (byte) Integer.parseInt(uuid.charAt(j) + "" + uuid.charAt(j + 1), 16);
        }
        return b;
    }

}
