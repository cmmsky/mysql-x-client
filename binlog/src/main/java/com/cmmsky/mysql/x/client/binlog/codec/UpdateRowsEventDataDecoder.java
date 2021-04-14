package com.cmmsky.mysql.x.client.binlog.codec;

import com.cmmsky.mysql.x.client.binlog.packet.EventData;
import com.cmmsky.mysql.x.client.binlog.packet.UpdateRowsEventData;

/**
 * @Author: cmmsky
 * @Date: Created in 15:53 2021/4/13
 * @Description:
 * @Modified by:
 */
public class UpdateRowsEventDataDecoder implements EventDataDecoder<UpdateRowsEventData> {

    @Override
    public UpdateRowsEventData decode(byte[] data) {
        UpdateRowsEventData updateRowsEventData = new UpdateRowsEventData();
        updateRowsEventData.read(data);
        return updateRowsEventData;
    }
}
