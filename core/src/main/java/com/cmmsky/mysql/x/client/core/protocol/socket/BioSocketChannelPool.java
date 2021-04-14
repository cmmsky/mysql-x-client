package com.cmmsky.mysql.x.client.core.protocol.socket;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * @Author: cmmsky
 * @Date: Created in 17:35 2021/4/9
 * @Description:
 * @Modified by:
 */
public class BioSocketChannelPool extends SocketChannelPool {

    private static final int RECV_BUFFER_SIZE = 1024;
    private static final int SEND_BUFFER_SIZE = 1024;


    public static SocketChannel open(InetSocketAddress address) throws IOException {
        Socket socket = new Socket();
        socket.setTcpNoDelay(true);
        socket.setKeepAlive(true);
        socket.setReceiveBufferSize(RECV_BUFFER_SIZE);
        socket.setSendBufferSize(SEND_BUFFER_SIZE);
        socket.connect(address, 2000);
        return new BioSocketChannel(socket);
    }

}
