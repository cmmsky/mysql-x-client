package com.cmmsky.mysql.x.client.binlog.packet;

import com.cmmsky.mysql.x.client.core.protocol.packets.MySQLMessage;

/**
 * @Author: cmmsky
 * @Date: Created in 14:14 2021/4/9
 * @Description:
 * @Modified by:
 */
public class XidEventData implements EventData {

    private long xid;

    @Override
    public void read(byte[] data) {
        MySQLMessage mm = new MySQLMessage(data);
        xid = mm.readLong();
    }

    @Override
    public String toString() {
        return "XidEventData{" +
                "xid=" + xid +
                '}';
    }

    public long getXid() {
        return xid;
    }

    public void setXid(long xid) {
        this.xid = xid;
    }
}
