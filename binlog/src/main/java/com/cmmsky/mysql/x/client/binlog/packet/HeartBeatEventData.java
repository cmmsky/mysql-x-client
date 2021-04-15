package com.cmmsky.mysql.x.client.binlog.packet;

import com.cmmsky.mysql.x.client.binlog.BinlogDumpContext;

/**
 * @Author: cmmsky
 * @Date: Created in 15:51 2021/4/13
 * @Description:
 * @Modified by:
 */
public class HeartBeatEventData implements EventData {


    @Override
    public void read(byte[] data) {
        System.out.println(String.format("收到心跳数据包 %s", System.currentTimeMillis()));
    }

    @Override
    public String toString() {
        return "HeartBeatEventData{ 心跳事件 }";
    }
}
