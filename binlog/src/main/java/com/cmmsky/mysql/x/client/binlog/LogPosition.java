package com.cmmsky.mysql.x.client.binlog;

import java.io.Serializable;

/**
 * @Author: dongguapi
 * @Date: Created in 17:13 2021/4/15
 * @Description:
 * @Modified by:
 */
public class LogPosition implements Serializable {

    private String            journalName;
    private Long              position;
    private Long              serverId              = null;              // 记录一下位点对应的serverId
    private String            gtid                  = null;

    public LogPosition(String journalName, Long position, Long serverId) {
        this.journalName = journalName;
        this.position = position;
        this.serverId = serverId;
    }

    public String getJournalName() {
        return journalName;
    }

    public void setJournalName(String journalName) {
        this.journalName = journalName;
    }

    public Long getPosition() {
        return position;
    }

    public void setPosition(Long position) {
        this.position = position;
    }

    public Long getServerId() {
        return serverId;
    }

    public void setServerId(Long serverId) {
        this.serverId = serverId;
    }

    public String getGtid() {
        return gtid;
    }

    public void setGtid(String gtid) {
        this.gtid = gtid;
    }

    @Override
    public String toString() {
        return "LogPosition{" +
                "journalName='" + journalName + '\'' +
                ", position=" + position +
                ", serverId=" + serverId +
                ", gtid='" + gtid + '\'' +
                '}';
    }
}
