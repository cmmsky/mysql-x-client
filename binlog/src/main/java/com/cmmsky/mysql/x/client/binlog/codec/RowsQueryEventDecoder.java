package com.cmmsky.mysql.x.client.binlog.codec;

import com.cmmsky.mysql.x.client.binlog.packet.RowsQueryEventData;

/**
 * @Author: cmmsky
 * @Date: Created in 16:04 2021/4/13
 * @Description:
 * @Modified by:
 */
public class RowsQueryEventDecoder implements EventDataDecoder<RowsQueryEventData> {
    @Override
    public RowsQueryEventData decode(byte[] data) {
        RowsQueryEventData rowsQueryEventData = new RowsQueryEventData();
        rowsQueryEventData.read(data);
        return rowsQueryEventData;
    }
}
