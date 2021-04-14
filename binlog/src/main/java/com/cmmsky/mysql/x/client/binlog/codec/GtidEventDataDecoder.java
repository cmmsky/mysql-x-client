package com.cmmsky.mysql.x.client.binlog.codec;

import com.cmmsky.mysql.x.client.binlog.packet.GtidEventData;

/**
 * @Author: cmmsky
 * @Date: Created in 15:41 2021/4/13
 * @Description:
 * @Modified by:
 */
public class GtidEventDataDecoder implements EventDataDecoder<GtidEventData> {

    @Override
    public GtidEventData decode(byte[] data) {
        GtidEventData gtidEventData = new GtidEventData();
        gtidEventData.read(data);
        return gtidEventData;
    }
}
