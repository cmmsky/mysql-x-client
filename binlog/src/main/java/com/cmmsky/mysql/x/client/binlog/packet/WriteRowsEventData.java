package com.cmmsky.mysql.x.client.binlog.packet;

import com.cmmsky.mysql.x.client.binlog.BinlogDumpContext;
import com.cmmsky.mysql.x.client.binlog.DecoderFactory;
import com.cmmsky.mysql.x.client.core.protocol.packets.MySQLMessage;
import com.cmmsky.mysql.x.client.core.utils.ByteUtil;
import com.cmmsky.mysql.x.client.core.utils.DecodeUtil;


import java.util.BitSet;
import java.util.LinkedList;
import java.util.List;

/**
 * @Author: cmmsky
 * @Date: Created in 14:09 2021/4/9
 * @Description:
 * @Modified by:
 */
public class WriteRowsEventData extends AbstractRowsEventData implements EventData {

    private long tableId;
    private BitSet includedColumns;
    private List<Object[]> rows;

    @Override
    public void read(byte[] data) {
        MySQLMessage mm = new MySQLMessage(data);
        tableId = ByteUtil.byte2Long(mm.readBytes(6));
        mm.move(2);

        if (BinlogDumpContext.getBinlogDumpContext().isMayContainExtraInformation()) {
            int extraInfoLength = mm.readUB2();
            mm.move(extraInfoLength - 2);
        }
        includedColumns = DecodeUtil.readBitSet(mm, (int) mm.readLength(), true);
        rows = decodeRows(mm);
    }

    private List<Object[]> decodeRows(MySQLMessage mm) {
        TableMapEventData tableMapEvent = DecoderFactory.getTableMapEventData(tableId);
        ColumnSet includedColumns2 = new ColumnSet(includedColumns);
        List<Object[]> result = new LinkedList<Object[]>();
        // checksum the last 4bytes,if crc32
        while (mm.available() > BinlogDumpContext.getBinlogDumpContext().getChecksumType().getLength()) {
            result.add(decodeRows(tableMapEvent, includedColumns2, mm));
        }
        return result;
    }

    @Override
    public String toString() {
        return "WriteRowsEventData{" +
                "tableId=" + tableId +
                ", includedColumns=" + includedColumns +
                ", rows=" + rows +
                '}';
    }

    public long getTableId() {
        return tableId;
    }

    public void setTableId(long tableId) {
        this.tableId = tableId;
    }

    public BitSet getIncludedColumns() {
        return includedColumns;
    }

    public void setIncludedColumns(BitSet includedColumns) {
        this.includedColumns = includedColumns;
    }

    public List<Object[]> getRows() {
        return rows;
    }

    public void setRows(List<Object[]> rows) {
        this.rows = rows;
    }
}
