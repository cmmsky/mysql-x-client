package com.cmmsky.mysql.x.client.binlog.packet;

import com.cmmsky.mysql.x.client.core.protocol.packets.MySQLMessage;

/**
 * @Author: cmmsky
 * @Date: Created in 14:48 2021/4/9
 * @Description: 当sql语句中使用了AUTO_INCREMENT的字段或者LAST_INSERT_ID()函数；此事件没有被用在binlog_format为ROW模式的情况下。
 * @Modified by:
 */
public class IntVarEventData implements EventData {

    private byte type;
    private long value;


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

    public byte getType() {
        return type;
    }

    public void setType(byte type) {
        this.type = type;
    }

    public long getValue() {
        return value;
    }

    public void setValue(long value) {
        this.value = value;
    }
}
