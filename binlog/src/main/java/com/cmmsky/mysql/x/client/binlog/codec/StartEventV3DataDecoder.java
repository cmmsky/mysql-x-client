package com.cmmsky.mysql.x.client.binlog.codec;

import com.cmmsky.mysql.x.client.binlog.packet.StartEventV3Data;
import com.cmmsky.mysql.x.client.core.protocol.packets.MySQLMessage;

/**
 * @Author: cmmsky
 * @Date: Created in 14:59 2021/4/13
 * @Description:
 * @Modified by:
 */
public class StartEventV3DataDecoder implements EventDataDecoder<StartEventV3Data> {

    public StartEventV3Data decode(byte[] data) {
        MySQLMessage mm = new MySQLMessage(data);
        StartEventV3Data startEventV3Data = new StartEventV3Data();
        startEventV3Data.read(data);
        return startEventV3Data;
    }
}
