package com.cmmsky.mysql.x.client.core.protocol.packets;

import com.cmmsky.mysql.x.client.core.utils.MSC;

import java.io.IOException;

/**
 * @Author: cmmsky
 * @Date: Created in 11:11 2021/4/12
 * @Description:
 * @Modified by:
 */
public class HandshakePacket extends MySQLPacket {

    // private HeaderPacket headerPacket;

    // 协议版本 1个字节
    private byte protocolVersion = MSC.DEFAULT_PROTOCOL_VERSION;
    // 服务器版本，字符串，NULL终止字符
    private byte[] serverVersion;
    // 线程ID 4个字节
    private long threadId;
    // 挑战随机数
    private byte[] seed;
    // 服务器功能选项 server_capabilities,两个字节
    private int serverCapabilities;
    // 字节编码 1个字节
    private byte serverCharsetNumber;
    // 服务器状态 2个字节
    private int serverStatus;
    // 12字节：挑战随机数，用于数据库认证
    private byte[] restOfScrambleBuff;
    private byte[] authPluginName;


    public void read(BinaryPacket data) throws IOException {
        packetLength = data.packetLength;
        packetId = data.packetId;
        MySQLMessage mm = new MySQLMessage(data.getBody());
        // 协议版本号
        protocolVersion = mm.read();
        // 数据库版本信息
        serverVersion = mm.readBytesWithNull();
        // server启动的线程ID
        threadId = mm.readUB4();
        // 挑战随机数，用于数据库验证(后面还跟着一个结束符填充值0x00)
        seed = mm.readBytesWithNull();
        // 用于与客户协商通讯方式
        serverCapabilities = mm.readUB2();
        // 数据库编码
        serverCharsetNumber = mm.read();
        // 服务器状态
        serverStatus = mm.readUB2();
        // 预留字节
        mm.move(13);
        // 挑战随机数用于数据库验证（后面跟着结束符填充值0x00）
        restOfScrambleBuff = mm.readBytesWithNull();
    }

    public byte getProtocolVersion() {
        return protocolVersion;
    }

    public void setProtocolVersion(byte protocolVersion) {
        this.protocolVersion = protocolVersion;
    }

    public byte[] getServerVersion() {
        return serverVersion;
    }

    public void setServerVersion(byte[] serverVersion) {
        this.serverVersion = serverVersion;
    }

    public long getThreadId() {
        return threadId;
    }

    public void setThreadId(long threadId) {
        this.threadId = threadId;
    }

    public byte[] getSeed() {
        return seed;
    }

    public void setSeed(byte[] seed) {
        this.seed = seed;
    }

    public int getServerCapabilities() {
        return serverCapabilities;
    }

    public void setServerCapabilities(int serverCapabilities) {
        this.serverCapabilities = serverCapabilities;
    }

    public byte getServerCharsetNumber() {
        return serverCharsetNumber;
    }

    public void setServerCharsetNumber(byte serverCharsetNumber) {
        this.serverCharsetNumber = serverCharsetNumber;
    }

    public int getServerStatus() {
        return serverStatus;
    }

    public void setServerStatus(int serverStatus) {
        this.serverStatus = serverStatus;
    }

    public byte[] getRestOfScrambleBuff() {
        return restOfScrambleBuff;
    }

    public void setRestOfScrambleBuff(byte[] restOfScrambleBuff) {
        this.restOfScrambleBuff = restOfScrambleBuff;
    }

    public byte[] getAuthPluginName() {
        return authPluginName;
    }

    public void setAuthPluginName(byte[] authPluginName) {
        this.authPluginName = authPluginName;
    }
}
