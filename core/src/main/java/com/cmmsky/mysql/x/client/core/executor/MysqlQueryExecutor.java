package com.cmmsky.mysql.x.client.core.executor;

import com.cmmsky.mysql.x.client.core.MySQLConnection;
import com.cmmsky.mysql.x.client.core.protocol.packets.*;
import com.cmmsky.mysql.x.client.core.protocol.packets.request.CommandPacket;
import com.cmmsky.mysql.x.client.core.utils.ErrorPacketException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: cmmsky
 * @Date: Created in 15:58 2021/4/12
 * @Description: 查询执行器
 * @Modified by:
 */
public class MysqlQueryExecutor extends MysqlExecutor {

    private MysqlQueryExecutor(MySQLConnection conn) {
        super(conn);
    }

    public ResultSetPacket query(String cmd) throws Exception {
        if (!connection.isStart()) {
            throw new Exception("connection not open");
        }
        CommandPacket commandPacket = new CommandPacket();
        commandPacket.setCommand(MySQLPacket.COM_QUERY);
        commandPacket.setArg(cmd.getBytes());
        commandPacket.write(connection.getChannel());
        BinaryPacket receive = receive();
        if (receive.getBody()[0] < 0) {
            ErrorPacketException.handleFailure(receive);
        }
        ResultSetHeaderPacket resultSetHeaderPacket = new ResultSetHeaderPacket();
        resultSetHeaderPacket.read(receive);

        List<FieldPacket> fieldPackets = new ArrayList<>();
        for (int i = 0; i < resultSetHeaderPacket.getFieldCount(); i++) {
            FieldPacket fieldPacket = new FieldPacket();
            fieldPacket.read(receive());
            fieldPackets.add(fieldPacket);
        }
        readEofPacket();

        List<RowDataPacket> rowDatas = new ArrayList<>();
        while (true) {
            BinaryPacket rowBin = receive();
            if (rowBin.getBody()[0] == EOFPacket.FIELD_COUNT) {
                break;
            }

            RowDataPacket rowDataPacket = new RowDataPacket(resultSetHeaderPacket.getFieldCount());
            rowDataPacket.read(rowBin);
            rowDatas.add(rowDataPacket);

        }

        ResultSetPacket resultSetPacket = new ResultSetPacket();
        resultSetPacket.setFieldDescriptors(fieldPackets);
        for (RowDataPacket rowDataPacket : rowDatas) {
            resultSetPacket.getFieldValues().addAll(rowDataPacket.getFieldValuesStrs());
        }
        return resultSetPacket;

    }

    private boolean readEofPacket() throws IOException {
        BinaryPacket receive = receive();
        EOFPacket eofPacket = new EOFPacket();
        eofPacket.read(receive);
        if (receive.getBody()[0] != -2) {
            throw new IOException("EOF Packet is expected, but packet with field_count=" + receive.getBody()[0] + " is found.");
        }
        return (eofPacket.getStatus() & 0x0008) != 0;
    }



}
