package com.cmmsky.mysql.x.client.core;

import com.cmmsky.mysql.x.client.core.executor.MysqlQueryExecutor;
import com.cmmsky.mysql.x.client.core.executor.MysqlUpdateExecutor;
import com.cmmsky.mysql.x.client.core.protocol.socket.SocketChannel;
import sun.misc.LRUCache;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * @Author: cmmsky
 * @Date: Created in 16:54 2021/4/9
 * @Description:
 * @Modified by:
 */
public class MySQLConnection {

    private SocketChannel channel;
    private Long threadId;
    private final Integer cacheSize = 10;

    LRUCache executorUpdateCache;
    LRUCache executorQueryCache;

    public MySQLConnection(SocketChannel channel, Long threadId) {
        this.channel = channel;
        this.threadId = threadId;

    }

    public MysqlUpdateExecutor getUpdateExecutor() throws Exception {
        Class<?> aClass = null;
        try {
            aClass = Class.forName(MysqlUpdateExecutor.class.getName());
            Constructor<?> constructor = aClass.getDeclaredConstructor(MySQLConnection.class);
            constructor.setAccessible(true);
            Object o = constructor.newInstance(this);
            return (MysqlUpdateExecutor) o;
        } catch (ClassNotFoundException | NoSuchMethodException e) {
            throw new Exception(e.getMessage());
        } catch (IllegalAccessException e) {
            throw new Exception(e.getMessage());
        } catch (InstantiationException e) {
            throw new Exception(e.getMessage());
        } catch (InvocationTargetException e) {
            throw new Exception(e.getMessage());
        }
        // Object obj = aClass.newInstance();

    }

    public MysqlQueryExecutor getQueryExecutor() throws Exception {
        Class<?> aClass = null;
        try {
            aClass = Class.forName(MysqlQueryExecutor.class.getName());
            // Object obj = aClass.newInstance();
            Constructor<?> constructor = aClass.getDeclaredConstructor(MySQLConnection.class);
            constructor.setAccessible(true);
            Object o = constructor.newInstance(this);
            return (MysqlQueryExecutor) o;
        } catch (ClassNotFoundException | NoSuchMethodException e) {
            throw new Exception(e.getMessage());
        } catch (IllegalAccessException e) {
            throw new Exception(e.getMessage());
        } catch (InstantiationException e) {
            throw new Exception(e.getMessage());
        } catch (InvocationTargetException e) {
            throw new Exception(e.getMessage());
        }
    }

    public SocketChannel getChannel() {
        return channel;
    }

    public void setChannel(SocketChannel channel) {
        this.channel = channel;
    }

    public void close() {
        // CommandPacket commandPacket = new CommandPacket(MySQLPacket.COM_PROCESS_KILL, threadId.toString().getBytes());
        if (channel != null) {
            channel.close();
        }
    }


}
