package com.cmmsky.mysql.x.client.core.protocol.packets;

import com.cmmsky.mysql.x.client.core.protocol.socket.SocketChannel;
import com.cmmsky.mysql.x.client.core.utils.StreamUtil;

import java.io.IOException;

/**
 * @Author: cmmsky
 * @Date: Created in 17:03 2021/4/9
 * @Description:
 * @Modified by:
 */
public class BinaryPacket extends MySQLPacket {

    private byte[] body;

    public void read(SocketChannel channel) throws IOException {
        packetLength = StreamUtil.readUB3(channel);
        packetId = StreamUtil.read(channel);
        byte [] bt = new byte[packetLength];
        StreamUtil.read(channel, bt, 0, packetLength);
        body = bt;
    }

    public byte[] getBody() {
        return body;
    }

    public void setBody(byte[] body) {
        this.body = body;
    }

    public byte getPacketId() {
        return this.packetId;
    }

}
