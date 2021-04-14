package com.cmmsky.mysql.x.client.core.protocol.packets;

/**
 * @Author: cmmsky
 * @Date: Created in 16:36 2021/4/12
 * @Description:
 * @Modified by:
 */
public class ResultSetHeaderPacket extends MySQLPacket {

    private int fieldCount;
    private long extra;

    public void read(BinaryPacket bin) {
        this.packetLength = bin.packetLength;
        this.packetId = bin.packetId;
        MySQLMessage mm = new MySQLMessage(bin.getBody());
        this.fieldCount = (int) mm.readLength();
        if (mm.hasRemaining()) {
            this.extra = mm.readLength();
        }


    }

    public int getFieldCount() {
        return fieldCount;
    }

    public void setFieldCount(int fieldCount) {
        this.fieldCount = fieldCount;
    }

    public long getExtra() {
        return extra;
    }

    public void setExtra(long extra) {
        this.extra = extra;
    }
}
