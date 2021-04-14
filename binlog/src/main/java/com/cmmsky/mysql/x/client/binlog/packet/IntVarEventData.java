package com.cmmsky.mysql.x.client.binlog.packet;

import com.cmmsky.mysql.x.client.core.protocol.packets.MySQLMessage;

/**
 * @Author: cmmsky
 * @Date: Created in 14:48 2021/4/9
 * @Description:
 * @Modified by:
 */
public class IntVarEventData implements EventData {

    public byte type;
    public long value;


    @Override
    public void read(byte[] data) {
        MySQLMessage mm = new MySQLMessage(data);
        type = mm.read();
        value = mm.readLong();
    }


    @Override
    public String toString() {
        return "InVarEventData{" +
                "type=" + type +
                ", value=" + value +
                '}';
    }
}
