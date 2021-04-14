package com.cmmsky.mysql.x.client.core.protocol.packets.request;

import com.cmmsky.mysql.x.client.core.protocol.packets.MySQLPacket;
import com.cmmsky.mysql.x.client.core.protocol.socket.SocketChannel;
import com.cmmsky.mysql.x.client.core.utils.StreamUtil;

import java.io.IOException;

/**
 * @Author: cmmsky
 * @Date: Created in 11:03 2021/4/13
 * @Description:
 * @Modified by:
 */
public class BinlogDumpCommandPacket extends MySQLPacket {

    public static final int BINLOG_DUMP_NON_BLOCK           = 1;
    public static final int BINLOG_SEND_ANNOTATE_ROWS_EVENT = 2;

    private byte command;

    // 二进制日志数据的启示位置
    private long   binlogPosition;
    // 二进制日志数据标志位
    private int    binlogFlags;
    // 从服务器的服务器ID值
    private int   slaveServerId;
    // 二进制文件名称
    private String binlogFileName;


    public void write(SocketChannel channel) throws IOException {
        // 写入header和 command
        StreamUtil.writeUB3(channel, getPacketLength());
        StreamUtil.write(channel, packetId);
        StreamUtil.write(channel, command);

        // StreamUtil.writeUB3(channel, getPacketLength());
        // StreamUtil.write(channel, packetId);
        StreamUtil.writeUB4(channel, binlogPosition);
        StreamUtil.writeUB2(channel, binlogFlags);
        StreamUtil.writeUB4(channel, slaveServerId);
        if (binlogFileName != null) {
            StreamUtil.writeWithNull(channel, binlogFileName.getBytes());
        }

    }


    protected int getPacketLength() {
        return 1 + 4 + 2 + 4 + (binlogFileName == null ? 0: binlogFileName.getBytes().length);
    }

    public byte getCommand() {
        return command;
    }

    public void setCommand(byte command) {
        this.command = command;
    }

    public long getBinlogPosition() {
        return binlogPosition;
    }

    public void setBinlogPosition(long binlogPosition) {
        this.binlogPosition = binlogPosition;
    }

    public int getBinlogFlags() {
        return binlogFlags;
    }

    public void setBinlogFlags(int binlogFlags) {
        this.binlogFlags = binlogFlags;
    }

    public int getSlaveServerId() {
        return slaveServerId;
    }

    public void setSlaveServerId(int slaveServerId) {
        this.slaveServerId = slaveServerId;
    }

    public String getBinlogFileName() {
        return binlogFileName;
    }

    public void setBinlogFileName(String binlogFileName) {
        this.binlogFileName = binlogFileName;
    }

    public void setPacketId(byte packetId) {
        this.packetId = packetId;
    }
}
