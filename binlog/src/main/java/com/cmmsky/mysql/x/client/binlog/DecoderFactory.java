package com.cmmsky.mysql.x.client.binlog;

import com.cmmsky.mysql.x.client.binlog.codec.*;
import com.cmmsky.mysql.x.client.binlog.packet.Event;
import com.cmmsky.mysql.x.client.binlog.packet.EventData;
import com.cmmsky.mysql.x.client.binlog.packet.RowsQueryEventData;
import com.cmmsky.mysql.x.client.binlog.packet.TableMapEventData;
import com.cmmsky.mysql.x.client.binlog.util.EventType;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author: cmmsky
 * @Date: Created in 15:30 2021/4/13
 * @Description:
 * @Modified by:
 */
public class DecoderFactory {

    public static Map<Long, TableMapEventData> tableMapEventDataByTableId = new HashMap<>();

    public static Map<Byte, EventDataDecoder> decoderMap;

    static {
        decoderMap = new ConcurrentHashMap<>();
        decoderMap.put(EventType.FORMAT_DESCRIPTION_EVENT, new FormatDescEventDataDecoder());
        decoderMap.put(EventType.ROTATE_EVENT, new RotateEventDataDecoder());
        decoderMap.put(EventType.INTVAR_EVENT, new IntVarEventDataDecoder());
        decoderMap.put(EventType.QUERY_EVENT, new QueryEventDataDecoder());
        decoderMap.put(EventType.TABLE_MAP_EVENT, new TableMapEventDataDecoder());
        decoderMap.put(EventType.XID_EVENT, new XidEventDataDecoder());
        decoderMap.put(EventType.WRITE_ROWS_EVENTv1, new WriteRowsEventDataDecoder());
        // ecoderMap.put(EventType.)
        decoderMap.put(EventType.UPDATE_ROWS_EVENTv1, new UpdateRowsEventDataDecoder());
        decoderMap.put(EventType.DELETE_ROWS_EVENTv1, new DeleteRowsEventDataDecoder());
        decoderMap.put(EventType.ROWS_QUERY_EVENT, new RowsQueryEventDecoder());
        decoderMap.put(EventType.ANONYMOUS_GTID_EVENT, new GtidEventDataDecoder());
        decoderMap.put(EventType.GTID_EVENT, new GtidEventDataDecoder());
        decoderMap.put(EventType.HEARTBEAT_EVENT, new HeartBeatEventDataDecoder());
    }

    public static EventDataDecoder getDecoder(byte eventType)  {
        return decoderMap.get(eventType);
    }


    public static void putTableMap(Long tableId, TableMapEventData tableMapEventData) {
        tableMapEventDataByTableId.put(tableId, tableMapEventData);
    }


    public static TableMapEventData getTableMapEventData(Long tableId) {
        TableMapEventData tableMapEventData = tableMapEventDataByTableId.get(tableId);
        if (tableMapEventData == null) {
            throw new RuntimeException("not match table map tableId = " + tableId);
        }
        return tableMapEventData;
    }
}
