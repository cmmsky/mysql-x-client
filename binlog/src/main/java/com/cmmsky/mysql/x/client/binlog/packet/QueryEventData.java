package com.cmmsky.mysql.x.client.binlog.packet;

import com.cmmsky.mysql.x.client.binlog.BinlogDumpContext;
import com.cmmsky.mysql.x.client.binlog.ChecksumType;
import com.cmmsky.mysql.x.client.core.protocol.packets.MySQLMessage;

/**
 * @Author: cmmsky
 * @Date: Created in 16:22 2021/4/6
 * @Description:
 * @Modified by:
 */
public class QueryEventData implements EventData {

    private long threadId;
    private long exectionTime;
    private byte schemeLength;
    private int errorCode;
    private int statusVarsLength;
    private byte[] statusVars;
    private String database;

    private String sql;


    @Override
    public void read(byte[] data) {
        MySQLMessage mm = new MySQLMessage(data);
        threadId = mm.readUB4();
        exectionTime = mm.readUB4();
        schemeLength = mm.read();
        errorCode = mm.readUB2();
        statusVarsLength = mm.readUB2();
        statusVars = mm.readBytes(statusVarsLength);
        database = new String(mm.readBytes(schemeLength));
        BinlogDumpContext context = BinlogDumpContext.getBinlogDumpContext();
        if (context.getChecksumType() == ChecksumType.CRC32) {
            sql = new String(mm.readBytesWithCrc32());
        } else {
            sql = new String(mm.readBytes());
        }
    }

    @Override
    public String toString() {
        return "QueryEventData{" +
                "threadId=" + threadId +
                ", exectionTime=" + exectionTime +
                ", schemeLength=" + schemeLength +
                ", errorCode=" + errorCode +
                ", statusVarsLength=" + statusVarsLength +
                ", statusVars='" + statusVars + '\'' +
                ", scheme='" + database + '\'' +
                ", query='" + sql + '\'' +
                '}';
    }

    public long getThreadId() {
        return threadId;
    }

    public void setThreadId(long threadId) {
        this.threadId = threadId;
    }

    public long getExectionTime() {
        return exectionTime;
    }

    public void setExectionTime(long exectionTime) {
        this.exectionTime = exectionTime;
    }

    public byte getSchemeLength() {
        return schemeLength;
    }

    public void setSchemeLength(byte schemeLength) {
        this.schemeLength = schemeLength;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public int getStatusVarsLength() {
        return statusVarsLength;
    }

    public void setStatusVarsLength(int statusVarsLength) {
        this.statusVarsLength = statusVarsLength;
    }

    public byte[] getStatusVars() {
        return statusVars;
    }

    public void setStatusVars(byte[] statusVars) {
        this.statusVars = statusVars;
    }

    public String getDatabase() {
        return database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }
}
