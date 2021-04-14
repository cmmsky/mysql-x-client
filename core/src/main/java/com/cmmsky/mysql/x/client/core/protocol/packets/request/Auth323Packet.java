package com.cmmsky.mysql.x.client.core.protocol.packets.request;

import com.cmmsky.mysql.x.client.core.protocol.packets.MySQLPacket;
import com.cmmsky.mysql.x.client.core.protocol.socket.SocketChannel;
import com.cmmsky.mysql.x.client.core.utils.StreamUtil;

import java.io.IOException;

/**
 * @Author: cmmsky
 * @Date: Created in 15:22 2021/4/12
 * @Description:
 * @Modified by:
 */
public class Auth323Packet extends MySQLPacket {

    private byte[] seed;

    public void write(SocketChannel channel) throws IOException {
        StreamUtil.writeUB3(channel, getPacketLength());
        StreamUtil.write(channel, packetId);
        if (seed == null) {
            StreamUtil.write(channel, (byte) 0);
        } else {
            StreamUtil.writeWithNull(channel, seed);
        }
    }

    protected int getPacketLength() {
        return seed == null ? 1 : seed.length + 1;
    }

    public void setPacketId(byte packetId) {
        this.packetId = packetId;
    }

    public void setSeed(byte[] seed) {
        this.seed = seed;
    }
}
