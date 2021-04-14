package com.cmmsky.mysql.x.client.binlog.codec;

import com.cmmsky.mysql.x.client.binlog.packet.XidEventData;

/**
 * @Author: cmmsky
 * @Date: Created in 15:46 2021/4/13
 * @Description:
 * @Modified by:
 */
public class XidEventDataDecoder implements EventDataDecoder<XidEventData>{
    @Override
    public XidEventData decode(byte[] data) {
        XidEventData xidEventData = new XidEventData();
        xidEventData.read(data);
        return xidEventData;
    }
}
