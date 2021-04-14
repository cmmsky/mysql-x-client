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

    public long threadId;
    public long exectionTime;
    public byte schemeLength;
    public int errorCode;
    public int statusVarsLength;
    public byte[] statusVars;
    public String database;

    public String sql;


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


}
