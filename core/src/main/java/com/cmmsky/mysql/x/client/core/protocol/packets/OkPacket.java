package com.cmmsky.mysql.x.client.core.protocol.packets;

import com.cmmsky.mysql.x.client.core.utils.ByteUtil;

import java.util.Arrays;

/**
 * @Author: cmmsky
 * @Date: Created in 14:41 2021/4/12
 * @Description:
 * @Modified by:
 */
public class OkPacket extends MySQLPacket {

    public static final byte FIELD_COUNT = 0x00;

    private byte fieldCount = FIELD_COUNT;
    private long affectedRows;
    private long insertId;
    private int serverStatus;
    private int warningCount;
    private byte[] message;

    public void read(BinaryPacket bin) {
        MySQLMessage mm = new MySQLMessage(bin.getBody());
        this.fieldCount = mm.read();
        this.affectedRows = ByteUtil.byte2Long(mm.readBytesWithLength());
        this.insertId = ByteUtil.byte2Long(mm.readBytesWithLength());
        this.serverStatus = mm.readUB2();
        this.warningCount = mm.readUB2();
        this.message = mm.readBytes();
    }

    public byte getFieldCount() {
        return fieldCount;
    }

    public void setFieldCount(byte fieldCount) {
        this.fieldCount = fieldCount;
    }

    public long getAffectedRows() {
        return affectedRows;
    }

    public void setAffectedRows(long affectedRows) {
        this.affectedRows = affectedRows;
    }

    public long getInsertId() {
        return insertId;
    }

    public void setInsertId(long insertId) {
        this.insertId = insertId;
    }

    public int getServerStatus() {
        return serverStatus;
    }

    public void setServerStatus(int serverStatus) {
        this.serverStatus = serverStatus;
    }

    public int getWarningCount() {
        return warningCount;
    }

    public void setWarningCount(int warningCount) {
        this.warningCount = warningCount;
    }

    public byte[] getMessage() {
        return message;
    }

    public void setMessage(byte[] message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "OkPacket{" +
                "fieldCount=" + fieldCount +
                ", affectedRows=" + affectedRows +
                ", insertId=" + insertId +
                ", serverStatus=" + serverStatus +
                ", warningCount=" + warningCount +
                ", message=" + Arrays.toString(message) +
                '}';
    }
}
