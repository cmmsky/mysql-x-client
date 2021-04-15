package com.cmmsky.mysql.x.client.binlog.packet;

import com.cmmsky.mysql.x.client.core.protocol.packets.MySQLMessage;

/**
 * @Author: cmmsky
 * @Date: Created in 18:02 2021/4/8
 * @Description:
 * @Modified by:
 */
public class GtidEventData implements EventData {

    private String gtid;
    private byte flags;


    @Override
    public void read(byte[] data) {
        MySQLMessage mm = new MySQLMessage(data);
        flags = mm.read();
        byte[] sid = mm.readBytes(16);
        long gno = mm.readLong();
        gtid = byteArrayToHex(sid, 0, 4) + "-" +
                byteArrayToHex(sid, 4, 2) + "-" +
                byteArrayToHex(sid, 6, 2) + "-" +
                byteArrayToHex(sid, 8, 2) + "-" +
                byteArrayToHex(sid, 10, 6) + ":" +
                String.format("%d", gno);
    }
    private String byteArrayToHex(byte[] a, int offset, int len) {
        StringBuilder sb = new StringBuilder();
        for (int idx = offset; idx < (offset + len) && idx < a.length; idx++) {
            sb.append(String.format("%02x", a[idx] & 0xff));
        }
        return sb.toString();
    }

    @Override
    public String toString() {
        return "GtidEventData{" +
                "gtid='" + gtid + '\'' +
                ", flags=" + flags +
                '}';
    }

    public String getGtid() {
        return gtid;
    }

    public void setGtid(String gtid) {
        this.gtid = gtid;
    }

    public byte getFlags() {
        return flags;
    }

    public void setFlags(byte flags) {
        this.flags = flags;
    }
}
