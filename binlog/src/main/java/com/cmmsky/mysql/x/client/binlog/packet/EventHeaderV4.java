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

    private long timestamp;
    // eventType在EventHeaader中
    private long serverId;
    private long eventSize;

    // if binlog-version > 1:
    private long nextLogPos;
    private int  flags;

    private int checksumAlg;
    private long crc;
    private String logFileName;

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
                ", nextLogPos=" + nextLogPos +
                ", flags=" + flags +
                '}';
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public long getServerId() {
        return serverId;
    }

    public void setServerId(long serverId) {
        this.serverId = serverId;
    }

    public long getEventSize() {
        return eventSize;
    }

    public void setEventSize(long eventSize) {
        this.eventSize = eventSize;
    }

    public long getNextLogPos() {
        return nextLogPos;
    }

    public void setNextLogPos(long nextLogPos) {
        this.nextLogPos = nextLogPos;
    }

    public int getFlags() {
        return flags;
    }

    public void setFlags(int flags) {
        this.flags = flags;
    }

    public int getChecksumAlg() {
        return checksumAlg;
    }

    public void setChecksumAlg(int checksumAlg) {
        this.checksumAlg = checksumAlg;
    }

    public long getCrc() {
        return crc;
    }

    public void setCrc(long crc) {
        this.crc = crc;
    }

    public String getLogFileName() {
        return logFileName;
    }

    public void setLogFileName(String logFileName) {
        this.logFileName = logFileName;
    }
}
