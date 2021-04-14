package com.cmmsky.mysql.x.client.core.protocol.socket;

import java.io.IOException;

/**
 * @Author: cmmsky
 * @Date: Created in 16:54 2021/4/9
 * @Description:
 * @Modified by:
 */
public interface SocketChannel {


    /**
     * 写入字节
     * @param bytes
     */
    public void write(byte[] bytes) throws IOException;

    public void write(int b) throws IOException;

    /**
     * 读取指定长度字节
     * @param
     * @return
     */
    public void read(byte[] data, int off, int len) throws IOException;

    /**
     * 读取单个字节
     * @return
     */
    public byte read() throws IOException;

    /**
     * 是否正在连接
     * @return
     */
    public boolean isConnected();

    /**
     * 关闭连接
     */
    public void close();

}
