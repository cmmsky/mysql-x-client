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

    private int binlogVersion;
    private String mysqServerlVersion;
    private long createTimestamp;
    private byte eventHeaderLength;
    private String eventTypeHeaderLengths;

    private byte dataLength;

    private ChecksumType checksumType;

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

    public int getBinlogVersion() {
        return binlogVersion;
    }

    public void setBinlogVersion(int binlogVersion) {
        this.binlogVersion = binlogVersion;
    }

    public String getMysqServerlVersion() {
        return mysqServerlVersion;
    }

    public void setMysqServerlVersion(String mysqServerlVersion) {
        this.mysqServerlVersion = mysqServerlVersion;
    }

    public long getCreateTimestamp() {
        return createTimestamp;
    }

    public void setCreateTimestamp(long createTimestamp) {
        this.createTimestamp = createTimestamp;
    }

    public byte getEventHeaderLength() {
        return eventHeaderLength;
    }

    public void setEventHeaderLength(byte eventHeaderLength) {
        this.eventHeaderLength = eventHeaderLength;
    }

    public String getEventTypeHeaderLengths() {
        return eventTypeHeaderLengths;
    }

    public void setEventTypeHeaderLengths(String eventTypeHeaderLengths) {
        this.eventTypeHeaderLengths = eventTypeHeaderLengths;
    }

    public byte getDataLength() {
        return dataLength;
    }

    public void setDataLength(byte dataLength) {
        this.dataLength = dataLength;
    }

    public ChecksumType getChecksumType() {
        return checksumType;
    }

    public void setChecksumType(ChecksumType checksumType) {
        this.checksumType = checksumType;
    }
}
