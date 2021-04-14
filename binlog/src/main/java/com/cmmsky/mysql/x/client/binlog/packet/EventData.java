package com.cmmsky.mysql.x.client.binlog.packet;


/**
 * @Author: cmmsky
 * @Date: Created in 14:15 2021/4/2
 * @Description:
 * @Modified by:
 */
public interface EventData {

    public int checksumLength = 0;

    public void read(byte[] data);
}
