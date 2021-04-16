package com.cmmsky.mysql.x.client.binlog.packet;

import com.cmmsky.mysql.x.client.binlog.BinlogDumpContext;

/**
 * @Author: cmmsky
 * @Date: Created in 17:57 2021/4/8
 * @Description: 执行LOAD DATA INFILE 语句时产生此事件，在MySQL 3.23版本中使用；
 * @Modified by:
 */
public class LoadEventData implements EventData {

    @Override
    public void read(byte[] data) {

    }
}
