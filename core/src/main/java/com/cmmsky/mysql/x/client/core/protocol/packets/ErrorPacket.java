package com.cmmsky.mysql.x.client.core.protocol.packets;

import java.util.Arrays;

/**
 * @Author: cmmsky
 * @Date: Created in 14:45 2021/4/12
 * @Description:
 * @Modified by:
 */
public class ErrorPacket extends MySQLPacket {

    public static final byte FIELD_COUNT = (byte) 0xff;
    private static final byte SQLSTATE_MARKER = (byte) '#';
    private static final byte[] DEFAULT_SQLSTATE = "HY000".getBytes();

    private byte fieldCount = FIELD_COUNT;
    private int errno;
    private byte mark = SQLSTATE_MARKER;
    private byte[] sqlState = DEFAULT_SQLSTATE;
    private byte[] message;

    public void read(BinaryPacket data) {
        packetLength = data.packetLength;
        packetId = data.packetId;
        MySQLMessage mm = new MySQLMessage(data.getBody());
        fieldCount = mm.read();
        errno = mm.readUB2();
        if (mm.hasRemaining() && (mm.read(mm.position()) == SQLSTATE_MARKER)) {
            mm.read();
            sqlState = mm.readBytes(5);
        }
        message = mm.readBytes();
    }


    @Override
    public String toString() {
        return "ErrorPacket{" +
                "fieldCount=" + fieldCount +
                ", errno=" + errno +
                ", mark=" + mark +
                ", sqlState=" + Arrays.toString(sqlState) +
                ", message=" + Arrays.toString(message) +
                '}';
    }

    public byte getFieldCount() {
        return fieldCount;
    }

    public void setFieldCount(byte fieldCount) {
        this.fieldCount = fieldCount;
    }

    public int getErrno() {
        return errno;
    }

    public void setErrno(int errno) {
        this.errno = errno;
    }

    public byte getMark() {
        return mark;
    }

    public void setMark(byte mark) {
        this.mark = mark;
    }

    public byte[] getSqlState() {
        return sqlState;
    }

    public void setSqlState(byte[] sqlState) {
        this.sqlState = sqlState;
    }

    public byte[] getMessage() {
        return message;
    }

    public void setMessage(byte[] message) {
        this.message = message;
    }
}
