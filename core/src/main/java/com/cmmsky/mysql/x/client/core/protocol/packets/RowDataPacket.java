package com.cmmsky.mysql.x.client.core.protocol.packets;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: cmmsky
 * @Date: Created in 16:38 2021/4/12
 * @Description:
 * @Modified by:
 */
public class RowDataPacket extends MySQLPacket {

    private final int fieldCount;
    private final List<byte[]> fieldValues;
    private final List<String> fieldValuesStrs;

    public RowDataPacket(int fieldCount) {
        this.fieldCount = fieldCount;
        this.fieldValues = new ArrayList<byte[]>(fieldCount);
        this.fieldValuesStrs = new ArrayList<>(fieldCount);
    }

    public void read(BinaryPacket bin) throws UnsupportedEncodingException {
        packetLength = bin.packetLength;
        packetId = bin.packetId;
        MySQLMessage mm = new MySQLMessage(bin.getBody());
        /*for (int i = 0; i < fieldCount; i++) {
            fieldValues.add(mm.readBytesWithLength());
        }*/
        do {
            byte[] bytes = mm.readBytesWithLength();
            fieldValues.add(bytes);
            fieldValuesStrs.add(new String(bytes, "UTF-8"));
        } while (mm.position() < bin.getBody().length);
    }

    public int getFieldCount() {
        return fieldCount;
    }

    public List<byte[]> getFieldValues() {
        return fieldValues;
    }

    public List<String> getFieldValuesStrs() {
        return fieldValuesStrs;
    }
}
