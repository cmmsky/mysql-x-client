package com.cmmsky.mysql.x.client.core.protocol.socket;

import java.io.IOException;
import java.net.InetSocketAddress;

/**
 * @Author: cmmsky
 * @Date: Created in 17:33 2021/4/9
 * @Description:
 * @Modified by:
 */
public abstract class SocketChannelPool {

    public static SocketChannel open(InetSocketAddress address) throws IOException {
        return  BioSocketChannelPool.open(address);
    }

}
