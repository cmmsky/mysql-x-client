package com.cmmsky.mysql.x.client.binlog.packet;

import com.cmmsky.mysql.x.client.core.protocol.packets.MySQLMessage;

/**
 * @Author: cmmsky
 * @Date: Created in 14:15 2021/4/2
 * @Description: binlog event header
 * @Modified by:
 */
public abstract class EventHeader {

    public byte eventType;

    public abstract void read(MySQLMessage mm);


}
