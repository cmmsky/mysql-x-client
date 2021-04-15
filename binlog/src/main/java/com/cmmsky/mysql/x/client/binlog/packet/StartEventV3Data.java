package com.cmmsky.mysql.x.client.binlog.packet;


import com.cmmsky.mysql.x.client.core.protocol.packets.MySQLMessage;

/**
 * @Author: cmmsky
 * @Date: Created in 16:09 2021/4/6
 * @Description:
 * @Modified by:
 */
public class StartEventV3Data implements EventData {

    private int binlogVersion;
    private String mysqlServerVersion;
    private long timeStamp;


    @Override
    public void read(byte[] data) {
        MySQLMessage mm = new MySQLMessage(data);
        binlogVersion = mm.readUB2();
        mysqlServerVersion = new String(mm.readBytes(50));
        timeStamp = mm.readUB4();
    }

    public int getBinlogVersion() {
        return binlogVersion;
    }

    public void setBinlogVersion(int binlogVersion) {
        this.binlogVersion = binlogVersion;
    }

    public String getMysqlServerVersion() {
        return mysqlServerVersion;
    }

    public void setMysqlServerVersion(String mysqlServerVersion) {
        this.mysqlServerVersion = mysqlServerVersion;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }
}
