package com.cmmsky.mysql.x.client.binlog.codec;

import com.cmmsky.mysql.x.client.binlog.packet.EventData;

/**
 * @Author: cmmsky
 * @Date: Created in 14:57 2021/4/13
 * @Description:
 * @Modified by:
 */
public interface EventDataDecoder<T extends EventData> {

    T decode(byte[] data);
}
