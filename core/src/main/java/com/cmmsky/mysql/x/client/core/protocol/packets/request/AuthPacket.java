package com.cmmsky.mysql.x.client.core.protocol.packets.request;

import com.cmmsky.mysql.x.client.core.protocol.socket.SocketChannel;
import com.cmmsky.mysql.x.client.core.utils.ByteUtil;
import com.cmmsky.mysql.x.client.core.utils.StreamUtil;

import java.io.IOException;

/**
 * @Author: cmmsky
 * @Date: Created in 11:24 2021/4/12
 * @Description:
 * @Modified by:
 */
public class AuthPacket {

    private static final byte[] FILLER = new byte[23];
    static {
        byte[] version = "1.0.0".getBytes();
        byte[] header = ByteUtil.getBytesWithLength(version.length);
        if ((header.length + version.length) <= FILLER.length) {
            int index = 0;
            for (int i = 0; i < header.length; i++) {
                FILLER[index++] = header[i];
            }
            for (int i = 0; i < version.length; i++) {
                FILLER[index++] = version[i];
            }
        }
    }

    private int packetLength;
    private byte packetId;


    private long clientFlags;
    private long maxPacketSize;
    private int charsetIndex;
    private String user;
    private byte[] password;
    private String database;

    public void write(SocketChannel channel) throws IOException {
        StreamUtil.writeUB3(channel, getPacketLength());
        StreamUtil.write(channel, packetId);
        StreamUtil.writeUB4(channel, clientFlags);
        StreamUtil.writeUB4(channel, maxPacketSize);
        StreamUtil.write(channel, (byte) charsetIndex);
        channel.write(FILLER);
        if (user == null) {
            StreamUtil.write(channel, (byte) 0);
        } else {
            StreamUtil.writeWithNull(channel, user.getBytes());
        }
        if (password == null) {
            StreamUtil.write(channel, (byte) 0);
        } else {
            StreamUtil.writeWithLength(channel, password);
        }
        if (database == null) {
            StreamUtil.write(channel, (byte) 0);
        } else {
            StreamUtil.writeWithNull(channel, database.getBytes());
        }
    }

    public static byte[] getFILLER() {
        return FILLER;
    }

    public int getPacketLength() {
        int size = 32;
        size += (user == null) ? 1: user.length() + 1;
        size += (password == null) ? 1 : ByteUtil.getLengthWithBytes(password);
        size += (database == null) ? 1 : database.length() + 1;
        return size;
    }

    public void setPacketLength(int packetLength) {
        this.packetLength = packetLength;
    }

    public byte getPacketId() {
        return packetId;
    }

    public void setPacketId(byte packetId) {
        this.packetId = packetId;
    }

    public long getClientFlags() {
        return clientFlags;
    }

    public void setClientFlags(long clientFlags) {
        this.clientFlags = clientFlags;
    }

    public long getMaxPacketSize() {
        return maxPacketSize;
    }

    public void setMaxPacketSize(long maxPacketSize) {
        this.maxPacketSize = maxPacketSize;
    }

    public int getCharsetIndex() {
        return charsetIndex;
    }

    public void setCharsetIndex(int charsetIndex) {
        this.charsetIndex = charsetIndex;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public byte[] getPassword() {
        return password;
    }

    public void setPassword(byte[] password) {
        this.password = password;
    }

    public String getDatabase() {
        return database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }
}
