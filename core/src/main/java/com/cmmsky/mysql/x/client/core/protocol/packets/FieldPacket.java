package com.cmmsky.mysql.x.client.core.protocol.packets;

import java.io.UnsupportedEncodingException;

/**
 * @Author: cmmsky
 * @Date: Created in 16:02 2021/4/12
 * @Description:
 * @Modified by:
 */
public class FieldPacket extends MySQLPacket {
    public String catalog;
    public String db;
    public String table;
    public String originalTable;
    public String name;
    public String originalName;
    public int    character;
    public long   length;
    public byte   type;
    public int    flags;
    public byte   decimals;
    public String definition;

    public void read(BinaryPacket bin) throws UnsupportedEncodingException {
        MySQLMessage mm = new MySQLMessage(bin.getBody());
        this.catalog = new String(mm.readBytesWithLength(), "UTF-8");
        this.db = new String(mm.readBytesWithLength());
        this.table = new String(mm.readBytesWithLength());
        this.originalTable = new String(mm.readBytesWithLength(), "UTF-8");
        this.name = new String(mm.readBytesWithLength(), "UTF-8");
        this.originalName = new String(mm.readBytesWithLength(), "UTF-8");
        this.character = mm.readUB2();
        this.length = mm.readUB4();
        this.type = mm.read();
        this.flags = mm.readUB2();
        this.decimals = mm.read();
        mm.move(2);
        this.definition = new String(mm.readBytesWithLength(), "UTF-8");

    }

}
