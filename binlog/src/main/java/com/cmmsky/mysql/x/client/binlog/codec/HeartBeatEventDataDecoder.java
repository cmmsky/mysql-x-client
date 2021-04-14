package com.cmmsky.mysql.x.client.binlog.codec;

import com.cmmsky.mysql.x.client.binlog.packet.HeartBeatEventData;

/**
 * @Author: cmmsky
 * @Date: Created in 15:51 2021/4/13
 * @Description:
 * @Modified by:
 */
public class HeartBeatEventDataDecoder implements EventDataDecoder<HeartBeatEventData> {
    @Override
    public HeartBeatEventData decode(byte[] data) {
        HeartBeatEventData heartBeatEventData = new HeartBeatEventData();
        heartBeatEventData.read(data);
        return heartBeatEventData;
    }
}
