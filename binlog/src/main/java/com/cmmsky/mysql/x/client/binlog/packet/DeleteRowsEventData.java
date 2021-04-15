package com.cmmsky.mysql.x.client.binlog.packet;

import com.cmmsky.mysql.x.client.binlog.BinlogDumpContext;
import com.cmmsky.mysql.x.client.binlog.DecoderFactory;
import com.cmmsky.mysql.x.client.core.protocol.packets.MySQLMessage;
import com.cmmsky.mysql.x.client.core.utils.ByteUtil;


import java.util.BitSet;
import java.util.LinkedList;
import java.util.List;

/**
 * @Author: cmmsky
 * @Date: Created in 20:02 2021/4/8
 * @Description:
 * @Modified by:
 */
public class DeleteRowsEventData extends AbstractRowsEventData implements EventData {

    private long tableId;
    private BitSet inCludedColumns;
    private List<Object[]> rows;


    @Override
    public void read(byte[] data) {
        MySQLMessage mm = new MySQLMessage(data);
        tableId = ByteUtil.byte2Long(mm.readBytes(6));
        // reserved
        mm.move(2);
        // 尝试获取额外信息
        if (BinlogDumpContext.getBinlogDumpContext().isMayContainExtraInformation()) {
            int i = mm.readUB2();
            mm.move(i - 2);
        }
        inCludedColumns = readBitSet(mm);
        rows = decodeRows(mm);
    }

    public List<Object[]> decodeRows(MySQLMessage mm) {
        TableMapEventData tableMapEvent = DecoderFactory.getTableMapEventData(tableId);
        ColumnSet includedColumns2 = new ColumnSet(inCludedColumns);
        List<Object[]> result = new LinkedList<Object[]>();
        // checksum the last 4bytes,if crc32
        while (mm.available() > BinlogDumpContext.getBinlogDumpContext().getChecksumType().getLength()) {
            result.add(decodeRows(tableMapEvent, includedColumns2, mm));
        }
        return result;
    }


    public BitSet readBitSet(MySQLMessage mm) {
        int length = (int) mm.readLength();
        // according to MySQL internals the amount of storage required for N columns is INT((N+7)/8) bytes
        byte[] bytes = mm.readBytes((length + 7) >> 3);
        BitSet bitSet = new BitSet();
        for (int i = 0; i < length; i++) {
            if ((bytes[i >> 3] & (i << (i % 8))) != 0) {
                bitSet.set(i);
            }
        }
        return bitSet;
    }

    @Override
    public String toString() {
        return "DeleteRowsEventData{" +
                "tableId=" + tableId +
                ", inCludedColumns=" + inCludedColumns +
                ", rows=" + rows +
                '}';
    }

    public long getTableId() {
        return tableId;
    }

    public void setTableId(long tableId) {
        this.tableId = tableId;
    }

    public BitSet getInCludedColumns() {
        return inCludedColumns;
    }

    public void setInCludedColumns(BitSet inCludedColumns) {
        this.inCludedColumns = inCludedColumns;
    }

    public List<Object[]> getRows() {
        return rows;
    }

    public void setRows(List<Object[]> rows) {
        this.rows = rows;
    }
}
