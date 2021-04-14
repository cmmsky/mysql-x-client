package com.cmmsky.mysql.x.client.binlog.packet;


import com.cmmsky.mysql.x.client.core.protocol.packets.MySQLMessage;

/**
 * @Author: cmmsky
 * @Date: Created in 15:57 2021/4/2
 * @Description:
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
