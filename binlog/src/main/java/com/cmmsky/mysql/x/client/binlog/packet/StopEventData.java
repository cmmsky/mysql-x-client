package com.cmmsky.mysql.x.client.binlog.packet;


/**
 * @Author: cmmsky
 * @Date: Created in 17:57 2021/4/8
 * @Description: 当mysqld停止时生成此事件
 * @Modified by:
 */
public class StopEventData implements EventData {
    @Override
    public void read(byte[] data) {

    }
}
