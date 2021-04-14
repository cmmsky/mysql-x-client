package com.cmmsky.mysql.x.client.binlog.packet;

import com.cmmsky.mysql.x.client.binlog.ChecksumType;
import com.cmmsky.mysql.x.client.binlog.util.EventType;
import com.cmmsky.mysql.x.client.core.protocol.packets.MySQLMessage;

/**
 * @Author: cmmsky
 * @Date: Created in 15:17 2021/4/2
 * @Description:
 * @Modified by:
 */
public class FormatDescEventData implements EventData {

    public int binlogVersion;
    public String mysqServerlVersion;
    public long createTimestamp;
    public byte eventHeaderLength;
    public String eventTypeHeaderLengths;

    private byte dataLength;

    public ChecksumType checksumType;

    @Override
    public String toString() {
        return "FormatDescEventData{" +
                "binlogVersion=" + binlogVersion +
                ", mysqServerlVersion='" + mysqServerlVersion + '\'' +
                ", createTimestamp=" + createTimestamp +
                ", eventHeaderLength=" + eventHeaderLength +
                ", eventTypeHeaderLengths='" + eventTypeHeaderLengths + '\'' +
                '}';
    }

    @Override
    public void read(byte[] data) {
        MySQLMessage mm = new MySQLMessage(data);
        binlogVersion = mm.readUB2();
        mysqServerlVersion = new String(mm.readBytes(50));
        createTimestamp = mm.readUB4();
        eventHeaderLength = mm.read();
        mm.move((int) EventType.FORMAT_DESCRIPTION_EVENT - 1);

        dataLength = mm.read();

        int checkSumBlockLength =  (int)data.length - dataLength;
        ChecksumType checksumType = ChecksumType.NONE;
        if (checkSumBlockLength > 0) {
            mm.move(mm.getLength() - mm.getPosition() - checkSumBlockLength);
            int read = (int)mm.read();
            checksumType = ChecksumType.byOrdinal(read);
            this.checksumType = checksumType;
        }
    }
}
