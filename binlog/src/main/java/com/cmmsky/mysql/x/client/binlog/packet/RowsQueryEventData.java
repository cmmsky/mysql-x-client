package com.cmmsky.mysql.x.client.binlog.packet;

import com.cmmsky.mysql.x.client.core.protocol.packets.MySQLMessage;

/**
 * @Author: cmmsky
 * @Date: Created in 14:51 2021/4/9
 * @Description:
 * @Modified by:
 */
public class RowsQueryEventData implements EventData {

    private String query;

    @Override
    public void read(byte[] data) {
        MySQLMessage mm = new MySQLMessage(data);
        int len = mm.read();
        query = new String(mm.readBytes(len));
    }


    @Override
    public String toString() {
        return "RowsQueryEventData{" +
                "query='" + query + '\'' +
                '}';
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }
}
