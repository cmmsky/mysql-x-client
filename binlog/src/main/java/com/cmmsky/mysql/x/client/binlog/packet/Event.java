package com.cmmsky.mysql.x.client.binlog.packet;

import com.cmmsky.mysql.x.client.binlog.DecoderFactory;
import com.cmmsky.mysql.x.client.binlog.codec.EventDataDecoder;
import com.cmmsky.mysql.x.client.core.protocol.packets.BinaryPacket;
import com.cmmsky.mysql.x.client.core.protocol.packets.MySQLMessage;

/**
 * @Author: cmmsky
 * @Date: Created in 14:14 2021/4/2
 * @Description:
 * @Modified by:
 * 判断binlog的version可以从第一个时间来判断
 *
 *a FORMAT_DESCRIPTION_EVENT version = 4
 *
 *a START_EVENT_V3
 *
 *  if event-size == 13 + 56: version = 1
 *  if event-size == 19 + 56: version = 3
 *  otherwise: invalid
 *
 */
public class Event {

    private EventHeader eventHeader;

    private EventData eventData;

    public Event(EventHeader header, EventData data) {
        this.eventHeader = header;
        this.eventData = data;
    }

    public void read(BinaryPacket bin) {
        MySQLMessage mm = new MySQLMessage(bin.getBody());
        mm.read();
        eventHeader = new EventHeaderV4();
        eventHeader.read(mm);
        EventDataDecoder decoder = DecoderFactory.getDecoder(eventHeader.eventType);
        if (decoder != null) {
            eventData = decoder.decode(mm.readBytes());
        } else {
            System.out.println(String.format("cannot find this event type %s", eventHeader.eventType));
        }

    }

    public Event() {
    }

    public EventHeader getEventHeader() {
        return eventHeader;
    }

    public void setEventHeader(EventHeader eventHeader) {
        this.eventHeader = eventHeader;
    }

    public EventData getEventData() {
        return eventData;
    }

    public void setEventData(EventData eventData) {
        this.eventData = eventData;
    }


    @Override
    public String toString() {
        return "Event{" +
                "eventHeader=" + eventHeader +
                ", eventData=" + eventData +
                '}';
    }
}
