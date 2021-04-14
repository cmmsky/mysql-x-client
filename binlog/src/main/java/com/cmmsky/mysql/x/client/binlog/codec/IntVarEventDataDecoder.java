package com.cmmsky.mysql.x.client.binlog.codec;

import com.cmmsky.mysql.x.client.binlog.packet.IntVarEventData;

/**
 * @Author: cmmsky
 * @Date: Created in 15:50 2021/4/13
 * @Description:
 * @Modified by:
 */
public class IntVarEventDataDecoder implements EventDataDecoder<IntVarEventData> {
    @Override
    public IntVarEventData decode(byte[] data) {
        IntVarEventData intVarEventData = new IntVarEventData();
        intVarEventData.read(data);
        return intVarEventData;
    }
}
