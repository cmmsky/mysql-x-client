package com.cmmsky.mysql.x.client.core.protocol.packets;

/**
 * @Author: cmmsky
 * @Date: Created in 15:12 2021/4/12
 * @Description:
 * @Modified by:
 */
public class EOFPacket extends MySQLPacket {

    public static final byte FIELD_COUNT = (byte) 0xfe;

    private byte fieldCount = FIELD_COUNT;
    private int warningCount;
    private int status = 2;

    public void read(BinaryPacket bin) {
        MySQLMessage mm = new MySQLMessage(bin.getBody());
        fieldCount = mm.read();
        warningCount = mm.readUB2();
        status = mm.readUB2();

    }

    public byte getFieldCount() {
        return fieldCount;
    }

    public void setFieldCount(byte fieldCount) {
        this.fieldCount = fieldCount;
    }

    public int getWarningCount() {
        return warningCount;
    }

    public void setWarningCount(int warningCount) {
        this.warningCount = warningCount;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
