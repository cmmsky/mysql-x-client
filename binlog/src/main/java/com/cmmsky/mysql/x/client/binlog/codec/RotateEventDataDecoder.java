package com.cmmsky.mysql.x.client.binlog.codec;

import com.cmmsky.mysql.x.client.binlog.packet.RotateEventData;

/**
 * @Author: cmmsky
 * @Date: Created in 15:12 2021/4/13
 * @Description:
 * @Modified by:
 */
public class RotateEventDataDecoder implements EventDataDecoder<RotateEventData> {

    @Override
    public RotateEventData decode(byte[] data) {
        RotateEventData rotateEventData = new RotateEventData();
        rotateEventData.read(data);
        return rotateEventData;
    }
}
