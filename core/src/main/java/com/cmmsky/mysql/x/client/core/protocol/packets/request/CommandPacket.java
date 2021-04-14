package com.cmmsky.mysql.x.client.core.protocol.packets.request;

import com.cmmsky.mysql.x.client.core.protocol.packets.MySQLPacket;
import com.cmmsky.mysql.x.client.core.protocol.socket.SocketChannel;
import com.cmmsky.mysql.x.client.core.utils.StreamUtil;

import java.io.IOException;

/**
 * @Author: cmmsky
 * @Date: Created in 16:03 2021/4/12
 * @Description:
 * @Modified by:
 */
public class CommandPacket extends MySQLPacket {

    private byte command;
    private byte[] arg;

    public CommandPacket(byte command, byte[] arg) {
        this.command = command;
        this.arg = arg;
    }

    public CommandPacket() {}

    public void write(SocketChannel channel) throws IOException {
        StreamUtil.writeUB3(channel, getPacketLength());
        StreamUtil.write(channel, packetId);
        StreamUtil.write(channel, command);
        channel.write(arg);
    }

    protected int getPacketLength() {
        return 1 + arg.length;
    }

    public byte getCommand() {
        return command;
    }

    public void setCommand(byte command) {
        this.command = command;
    }

    public byte[] getArg() {
        return arg;
    }

    public void setArg(byte[] arg) {
        this.arg = arg;
    }
}
