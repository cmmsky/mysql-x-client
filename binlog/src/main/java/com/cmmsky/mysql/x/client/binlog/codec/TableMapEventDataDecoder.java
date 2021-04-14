package com.cmmsky.mysql.x.client.binlog.codec;

import com.cmmsky.mysql.x.client.binlog.packet.TableMapEventData;

/**
 * @Author: cmmsky
 * @Date: Created in 15:42 2021/4/13
 * @Description:
 * @Modified by:
 */
public class TableMapEventDataDecoder implements EventDataDecoder<TableMapEventData> {
    @Override
    public TableMapEventData decode(byte[] data) {
        TableMapEventData tableMapEventData = new TableMapEventData();
        tableMapEventData.read(data);
        return tableMapEventData;
    }
}
