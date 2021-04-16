package com.cmmsky.mysql.x.client.core;

import com.cmmsky.mysql.x.client.core.common.AbstractBaseLifeCycle;
import com.cmmsky.mysql.x.client.core.protocol.packets.*;
import com.cmmsky.mysql.x.client.core.protocol.packets.request.Auth323Packet;
import com.cmmsky.mysql.x.client.core.protocol.packets.request.AuthPacket;
import com.cmmsky.mysql.x.client.core.protocol.socket.SocketChannel;
import com.cmmsky.mysql.x.client.core.protocol.socket.SocketChannelPool;
import com.cmmsky.mysql.x.client.core.utils.*;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.security.NoSuchAlgorithmException;

/**
 * @Author: cmmsky
 * @Date: Created in 17:18 2021/4/9
 * @Description:
 * @Modified by:
 */
public class MySQLDataSource extends AbstractBaseLifeCycle {

    private InetSocketAddress address;
    private String username;
    private String password;

    private static final long CLIENT_FLAGS = getClientFlags();
    private static final long MAX_PACKET_SIZE = 1024 * 1024 * 16;

    private MySQLConnection connection = null;

    public MySQLDataSource(String address, int port, String username, String passwd) {
        this.address = new InetSocketAddress(address, port);
        this.username = username;
        this.password = passwd;
    }

    public MySQLConnection getConnection() throws IOException {
        if (connection != null)
            return connection;
        SocketChannel channel = SocketChannelPool.open(address);
        connection = new MySQLConnection(channel, doAuthenticate(channel));
        return  connection;
    }

    private long doAuthenticate(SocketChannel channel) throws IOException {
        BinaryPacket greeting = new BinaryPacket();
        greeting.read(channel);
        HandshakePacket hsp = new HandshakePacket();
        // 初始化报文
        hsp.read(greeting);
        BinaryPacket authResp = null;
        try {
            authResp = auth411(hsp, channel);
        } catch (NoSuchAlgorithmException e) {
            throw new XException(String.format("权限验证发生异常 %s", e.getMessage()));
        }
        System.out.println(String.format("线程ID %s", hsp.getThreadId()));

        switch (authResp.getBody()[0]) {
            case OkPacket.FIELD_COUNT:

                break;
            case ErrorPacket
                    .FIELD_COUNT:
                ErrorPacket err = new ErrorPacket();
                err.read(authResp);
                throw new ErrorPacketException(new String(err.getMessage()));
            case EOFPacket.FIELD_COUNT:
                auth323(authResp.getPacketId(), hsp.getSeed(), channel);
                break;
            default:
                throw new UnknownPacketException(authResp.toString());
        }

        return hsp.getThreadId();

    }

    private BinaryPacket auth411(HandshakePacket hsp, SocketChannel channel) throws NoSuchAlgorithmException, IOException {
        AuthPacket ap = new AuthPacket();
        // 请求序号，前面初始化握手报文为0此处递增一位
        ap.setPacketId((byte)1);
        // 权能标志
        // ap.clientFlags = CLIENT_FLAGS;
        ap.setClientFlags(CLIENT_FLAGS);
        // 请求报文支持的最大长度值
        ap.setMaxPacketSize(MAX_PACKET_SIZE);
        // 字符编码
        ap.setCharsetIndex(hsp.getServerCharsetNumber() & 0xff);
        ap.setUser(username);
        String passwd = password;
        if (passwd != null && passwd.length() > 0) {
            byte[] password = passwd.getBytes();
            byte[] seed = hsp.getSeed();
            byte[] restOfScramble = hsp.getRestOfScrambleBuff();
            byte[] authSeed = new byte[seed.length + restOfScramble.length];
            System.arraycopy(seed, 0, authSeed, 0, seed.length);
            System.arraycopy(restOfScramble, 0, authSeed, seed.length, restOfScramble.length);
            ap.setPassword(SecurityUtil.scramble411(password, authSeed));
        }
        // ap.database =
        ap.write(channel);
        BinaryPacket bin = new BinaryPacket();
        bin.read(channel);
        return bin;
    }


    private void auth323(byte packetId, byte[] seed, SocketChannel channel) throws IOException {
        Auth323Packet a323 = new Auth323Packet();
        a323.setPacketId(packetId++);
        String passwd = password;
        if (passwd != null && passwd.length() > 0) {
            a323.setSeed(SecurityUtil.scramble323(passwd, new String(seed)).getBytes());
        }
        a323.write(channel);
        BinaryPacket bin = new BinaryPacket();
        bin.read(channel);
        switch (bin.getBody()[0]) {
            case OkPacket.FIELD_COUNT:
                break;
            case ErrorPacket.FIELD_COUNT:
                ErrorPacket err = new ErrorPacket();
                err.read(bin);
                throw new ErrorPacketException(new String(err.getMessage()));
            default:
                throw new UnknownPacketException(bin.toString());
        }

    }

    private static long getClientFlags() {
        int flag = 0;
        flag |= Capabilities.CLIENT_LONG_PASSWORD;
        flag |= Capabilities.CLIENT_FOUND_ROWS;
        flag |= Capabilities.CLIENT_LONG_FLAG;
        flag |= Capabilities.CLIENT_CONNECT_WITH_DB;
        // flag |= Capabilities.CLIENT_NO_SCHEMA;
        // flag |= Capabilities.CLIENT_COMPRESS;
        flag |= Capabilities.CLIENT_ODBC;
        // flag |= Capabilities.CLIENT_LOCAL_FILES;
        flag |= Capabilities.CLIENT_IGNORE_SPACE;
        flag |= Capabilities.CLIENT_PROTOCOL_41;
        flag |= Capabilities.CLIENT_INTERACTIVE;
        // flag |= Capabilities.CLIENT_SSL;
        flag |= Capabilities.CLIENT_IGNORE_SIGPIPE;
        flag |= Capabilities.CLIENT_TRANSACTIONS;
        // flag |= Capabilities.CLIENT_RESERVED;
        flag |= Capabilities.CLIENT_SECURE_CONNECTION;
        // client extension
        // flag |= Capabilities.CLIENT_MULTI_STATEMENTS;
        // flag |= Capabilities.CLIENT_MULTI_RESULTS;
        return flag;
    }


    @Override
    protected void doStart() {
        if (connection == null) {
            try {
                this.getConnection();
            } catch (IOException e) {
                throw new XException(String.format("连接数据库发生异常 %s", e.getMessage()));
            }
        }
        connection.start();
    }

    @Override
    protected void doStop() {
        connection.stop();
    }
}
