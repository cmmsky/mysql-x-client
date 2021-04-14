package com.cmmsky.mysql.x.client.binlog.codec;

import com.cmmsky.mysql.x.client.binlog.packet.WriteRowsEventData;

/**
 * @Author: cmmsky
 * @Date: Created in 15:44 2021/4/13
 * @Description:
 * @Modified by:
 */
public class WriteRowsEventDataDecoder implements EventDataDecoder<WriteRowsEventData> {
    @Override
    public WriteRowsEventData decode(byte[] data) {
        WriteRowsEventData writeRowsEventData = new WriteRowsEventData();
        writeRowsEventData.read(data);
        return writeRowsEventData;
    }
}
