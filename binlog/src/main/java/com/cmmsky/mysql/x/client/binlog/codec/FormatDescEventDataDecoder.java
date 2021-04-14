package com.cmmsky.mysql.x.client.binlog.codec;

import com.cmmsky.mysql.x.client.binlog.packet.FormatDescEventData;

/**
 * @Author: cmmsky
 * @Date: Created in 15:09 2021/4/13
 * @Description:
 * @Modified by:
 */
public class FormatDescEventDataDecoder implements EventDataDecoder<FormatDescEventData> {

    @Override
    public FormatDescEventData decode(byte[] data) {
        FormatDescEventData formatDescEventData = new FormatDescEventData();
        formatDescEventData.read(data);

        return formatDescEventData;
    }
}
