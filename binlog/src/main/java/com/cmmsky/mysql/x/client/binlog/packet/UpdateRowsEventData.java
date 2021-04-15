package com.cmmsky.mysql.x.client.binlog.packet;

import com.cmmsky.mysql.x.client.binlog.BinlogDumpContext;
import com.cmmsky.mysql.x.client.binlog.DecoderFactory;
import com.cmmsky.mysql.x.client.core.protocol.packets.MySQLMessage;
import com.cmmsky.mysql.x.client.core.utils.ByteUtil;
import com.cmmsky.mysql.x.client.core.utils.DecodeUtil;


import java.util.*;

/**
 * @Author: cmmsky
 * @Date: Created in 15:56 2021/4/9
 * @Description:
 * @Modified by:
 */
public class UpdateRowsEventData extends AbstractRowsEventData implements EventData {

    private long tableId;
    private BitSet includedColumnBeforeUpdate;
    private BitSet includedColumns;

    private List<Map.Entry<Object[], Object[]>> rows;

    @Override
    public void read(byte[] data) {
        MySQLMessage mm = new MySQLMessage(data);
        tableId = ByteUtil.byte2Long(mm.readBytes(6));
        mm.move(2);
        if (BinlogDumpContext.getBinlogDumpContext().isMayContainExtraInformation()) {
            int extraInfoLength = mm.readUB2();
            mm.move(extraInfoLength - 2);
        }
        int numberOfColumns = (int) mm.readLength();
        includedColumnBeforeUpdate = DecodeUtil.readBitSet(mm, numberOfColumns, true);
        includedColumns = DecodeUtil.readBitSet(mm, numberOfColumns, true);
        rows = decodeRows(mm);
    }

    private List<Map.Entry<Object[], Object[]>> decodeRows(MySQLMessage mm) {
        TableMapEventData tableMapEvent = DecoderFactory.getTableMapEventData(tableId);
        ColumnSet includedColumnsBeforeUpdate2 = new ColumnSet(includedColumnBeforeUpdate);
        ColumnSet includedColumns2 = new ColumnSet(includedColumns);
        List<Map.Entry<Object[], Object[]>> rows = new LinkedList<Map.Entry<Object[], Object[]>>();
        while (mm.available() > BinlogDumpContext.getBinlogDumpContext().getChecksumType().getLength()) {
            rows.add(new AbstractMap.SimpleEntry<Object[], Object[]>(
                    decodeRows(tableMapEvent, includedColumnsBeforeUpdate2, mm),
                    decodeRows(tableMapEvent, includedColumns2, mm)
            ));
        }
        return rows;
    }

    public long getTableId() {
        return tableId;
    }

    public void setTableId(long tableId) {
        this.tableId = tableId;
    }

    public BitSet getIncludedColumnBeforeUpdate() {
        return includedColumnBeforeUpdate;
    }

    public void setIncludedColumnBeforeUpdate(BitSet includedColumnBeforeUpdate) {
        this.includedColumnBeforeUpdate = includedColumnBeforeUpdate;
    }

    public BitSet getIncludedColumns() {
        return includedColumns;
    }

    public void setIncludedColumns(BitSet includedColumns) {
        this.includedColumns = includedColumns;
    }

    public List<Map.Entry<Object[], Object[]>> getRows() {
        return rows;
    }

    public void setRows(List<Map.Entry<Object[], Object[]>> rows) {
        this.rows = rows;
    }
}
