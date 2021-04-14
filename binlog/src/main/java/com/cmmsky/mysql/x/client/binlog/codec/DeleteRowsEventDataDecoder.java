package com.cmmsky.mysql.x.client.binlog.codec;

import com.cmmsky.mysql.x.client.binlog.packet.DeleteRowsEventData;

/**
 * @Author: cmmsky
 * @Date: Created in 15:47 2021/4/13
 * @Description:
 * @Modified by:
 */
public class DeleteRowsEventDataDecoder implements EventDataDecoder<DeleteRowsEventData>{
    @Override
    public DeleteRowsEventData decode(byte[] data) {
        DeleteRowsEventData deleteRowsEventData = new DeleteRowsEventData();
        deleteRowsEventData.read(data);
        return deleteRowsEventData;
    }
}
