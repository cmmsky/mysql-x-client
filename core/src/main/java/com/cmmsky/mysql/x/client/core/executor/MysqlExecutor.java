package com.cmmsky.mysql.x.client.core.executor;

import com.cmmsky.mysql.x.client.core.MySQLConnection;
import com.cmmsky.mysql.x.client.core.protocol.packets.BinaryPacket;

import java.io.IOException;

/**
 * @Author: cmmsky
 * @Date: Created in 15:54 2021/4/12
 * @Description:
 * @Modified by:
 */
public abstract class MysqlExecutor {

    protected MySQLConnection connection;

    protected MysqlExecutor(MySQLConnection conn) {
        this.connection = conn;
    }

    public BinaryPacket receive() throws IOException {
        BinaryPacket bin = new BinaryPacket();
        bin.read(connection.getChannel());
        return bin;
    }

}
