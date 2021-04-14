package com.cmmsky.mysql.x.client.binlog.codec;

import com.cmmsky.mysql.x.client.binlog.packet.QueryEventData;

/**
 * @Author: cmmsky
 * @Date: Created in 15:14 2021/4/13
 * @Description:
 * @Modified by:
 */
public class QueryEventDataDecoder implements EventDataDecoder<QueryEventData> {

    @Override
    public QueryEventData decode(byte[] data) {
        QueryEventData queryEventData = new QueryEventData();
        queryEventData.read(data);
        return queryEventData;
    }
}
