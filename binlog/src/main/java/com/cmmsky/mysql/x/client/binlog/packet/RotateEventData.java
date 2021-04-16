package com.cmmsky.mysql.x.client.binlog.packet;


import com.cmmsky.mysql.x.client.core.protocol.packets.MySQLMessage;

/**
 * @Author: cmmsky
 * @Date: Created in 15:57 2021/4/2
 * @Description: 当MySQL的binlog文件从file1滚动到file2的时候会发生此事件
 * 当mysqld切换到新的binlog文件生成此事件，切换到新的binlog文件可以通过执行flush logs命令或者binlog文件大于max_binlog_size参数配置的大小；
 * @Modified by:
 */
public class RotateEventData implements EventData {

    private String binlogFilename;
    // if binlog-version > 1 {
    //8              position
    //  }
    private long binlogPosition;

    @Override
    public void read(byte[] data) {
        MySQLMessage mm = new MySQLMessage(data);
        binlogPosition = mm.readLong();

        binlogFilename = new String(mm.readBytes());
    }


    @Override
    public String toString() {
        return "RotateEventData{" +
                "binlogFilename='" + binlogFilename + '\'' +
                ", binlogPosition=" + binlogPosition +
                '}';
    }


}
