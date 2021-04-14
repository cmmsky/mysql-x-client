package com.cmmsky.mysql.x.client.core.executor;

import com.cmmsky.mysql.x.client.core.MySQLConnection;
import com.cmmsky.mysql.x.client.core.protocol.packets.BinaryPacket;
import com.cmmsky.mysql.x.client.core.protocol.packets.ErrorPacket;
import com.cmmsky.mysql.x.client.core.protocol.packets.MySQLPacket;
import com.cmmsky.mysql.x.client.core.protocol.packets.OkPacket;
import com.cmmsky.mysql.x.client.core.protocol.packets.request.CommandPacket;

import java.io.IOException;

/**
 * @Author: cmmsky
 * @Date: Created in 15:57 2021/4/12
 * @Description:
 * @Modified by:
 */
public class MysqlUpdateExecutor extends MysqlExecutor {

    private MysqlUpdateExecutor(MySQLConnection conn) {
        super(conn);
    }

    public OkPacket update(String update) throws IOException {
        CommandPacket cmd = new CommandPacket();
        cmd.setCommand(MySQLPacket.COM_QUERY);
        cmd.setArg(update.getBytes());
        cmd.write(connection.getChannel());
        BinaryPacket receive = receive();

        if (receive.getBody()[0] < 0) {
            ErrorPacket packet = new ErrorPacket();
            packet.read(receive);
            throw new IOException(packet + "\n with command " + update);
        }
        OkPacket okPacket = new OkPacket();
        okPacket.read(receive);
        System.out.println(okPacket.toString());
        return okPacket;
    }
}
