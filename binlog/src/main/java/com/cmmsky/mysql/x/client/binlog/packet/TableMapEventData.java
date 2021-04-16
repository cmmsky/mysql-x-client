package com.cmmsky.mysql.x.client.binlog.packet;

import com.cmmsky.mysql.x.client.binlog.DecoderFactory;
import com.cmmsky.mysql.x.client.core.protocol.packets.MySQLMessage;
import com.cmmsky.mysql.x.client.core.utils.ByteUtil;
import com.cmmsky.mysql.x.client.core.utils.DecodeUtil;


import java.util.Arrays;
import java.util.BitSet;

/**
 * @Author: cmmsky
 * @Date: Created in 10:35 2021/4/9
 * @Description: 用在binlog_format为ROW模式下，将表的定义映射到一个数字，在行操作事件之前记录（包括：WRITE_ROWS_EVENT，UPDATE_ROWS_EVENT，DELETE_ROWS_EVENT）；
 * @Modified by:
 */
public class TableMapEventData implements EventData {

    private long tableId;
    private String database;
    private String table;
    private byte[] columnTypes;
    private int[] columnMetadata;
    private BitSet columnNullability;


    @Override
    public void read(byte[] data) {
        MySQLMessage mm = new MySQLMessage(data);
        tableId = ByteUtil.byte2Long(mm.readBytes(6));
        mm.move(3);
        database = ByteUtil.byte2String(mm.readBytesWithNull());
        mm.move(1);
        table = ByteUtil.byte2String(mm.readBytesWithNull());
        int numberOfColumns = (int) mm.readLength();
        columnTypes = mm.readBytes(numberOfColumns);
        mm.readLength();
        columnMetadata = readMetadata(mm, columnTypes);
        columnNullability = DecodeUtil.readBitSet(mm);
        // 自身注册进tableMap
        DecoderFactory.putTableMap(tableId, this);
    }

    private int[] readMetadata(MySQLMessage mm, byte[] columnTypes) {
        int[] metadata = new int[columnTypes.length];
        for (int i = 0; i < columnTypes.length; i++) {
            switch (ColumnType.byCode(columnTypes[i] & 0xFF)) {
                case FLOAT:
                case DOUBLE:
                case BLOB:
                case GEOMETRY:
                    metadata[i] = mm.read();
                    break;
                case BIT:
                case VARCHAR:
                case NEWDECIMAL:
                    metadata[i] = mm.readUB2();
                    break;
                case SET:
                case ENUM:
                case STRING:
                    metadata[i] = DecodeUtil.bigEndianInteger(mm.readBytes(2), 0, 2);
                    break;
                case TIME_V2:
                case DATETIME_V2:
                case TIMESTAMP_V2:
                    metadata[i] = mm.read();
                    break;
                default:
                    metadata[i] = 0;
            }
        }
        return metadata;
    }

    public long getTableId() {
        return tableId;
    }

    public void setTableId(long tableId) {
        this.tableId = tableId;
    }

    public String getDatabase() {
        return database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public byte[] getColumnTypes() {
        return columnTypes;
    }

    public void setColumnTypes(byte[] columnTypes) {
        this.columnTypes = columnTypes;
    }

    public int[] getColumnMetadata() {
        return columnMetadata;
    }

    public void setColumnMetadata(int[] columnMetadata) {
        this.columnMetadata = columnMetadata;
    }

    public BitSet getColumnNullability() {
        return columnNullability;
    }

    public void setColumnNullability(BitSet columnNullability) {
        this.columnNullability = columnNullability;
    }

    @Override
    public String toString() {
        return "TableMapEventData{" +
                "tableId=" + tableId +
                ", database='" + database + '\'' +
                ", table='" + table + '\'' +
                ", columnTypes=" + Arrays.toString(columnTypes) +
                ", columnMetadata=" + Arrays.toString(columnMetadata) +
                ", columnNullability=" + columnNullability +
                '}';
    }
}
