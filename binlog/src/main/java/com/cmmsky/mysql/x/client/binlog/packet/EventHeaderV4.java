package com.cmmsky.mysql.x.client.binlog.packet;

import com.cmmsky.mysql.x.client.binlog.util.EventType;
import com.cmmsky.mysql.x.client.core.protocol.packets.MySQLMessage;


/**
 * @Author: cmmsky
 * @Date: Created in 14:16 2021/4/2
 * @Description:
 * @Modified by:
 *
 * Binlog version   MySQL Version
 *
 * 1                MySQL 3.23 - < 4.0.0            supported statement based replication events
 * 2                MySQL 4.0.0 - 4.0.1             can be ignored as it was only used in early alpha versions of MySQL 4.1.x and won't be documented here
 * 3                MySQL 4.0.2 - < 5.0.0           added the relay logs and changed the meaning of the log position
 * 4                MySQL 5.0.0+                    added the FORMAT_DESCRIPTION_EVENT and made the protocol extensible In MySQL 5.1.x the Row Based Replication Events were added.
 *
 *
 */
public class EventHeaderV4 extends EventHeader {

    public long timestamp;
    // eventType在EventHeaader中
    public long serverId;
    public long eventSize;

    // if binlog-version > 1:
    public long nextLogPos;
    public int  flags;

    public int checksumAlg;
    public long crc;
    public String logFileName;

    public void read(MySQLMessage mm) {
        this.timestamp = mm.readUB4() * 1000;
        this.eventType = mm.read();
        this.serverId = mm.readUB4();
        this.eventSize = mm.readUB4();
        this.nextLogPos = mm.readUB4();
        this.flags = mm.readUB2();

        if (eventType == EventType.FORMAT_DESCRIPTION_EVENT || eventType == EventType.ROTATE_EVENT) {

        }
    }

    @Override
    public String toString() {
        return "EventHeaderV4{" +
                "timestamp=" + timestamp +
                ", eventType=" + eventType +
                ", serverId=" + serverId +
                ", eventSize=" + eventSize +
                ", logPos=" + nextLogPos +
                ", flags=" + flags +
                '}';
    }
}
