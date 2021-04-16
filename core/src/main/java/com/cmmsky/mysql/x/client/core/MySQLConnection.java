package com.cmmsky.mysql.x.client.core;

import com.cmmsky.mysql.x.client.core.common.AbstractBaseLifeCycle;
import com.cmmsky.mysql.x.client.core.executor.MysqlQueryExecutor;
import com.cmmsky.mysql.x.client.core.executor.MysqlUpdateExecutor;
import com.cmmsky.mysql.x.client.core.protocol.socket.SocketChannel;
import com.cmmsky.mysql.x.client.core.utils.LifeCycleException;
import com.cmmsky.mysql.x.client.core.utils.XException;
import sun.misc.LRUCache;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * @Author: cmmsky
 * @Date: Created in 16:54 2021/4/9
 * @Description:
 * @Modified by:
 */
public class MySQLConnection extends AbstractBaseLifeCycle {

    private SocketChannel channel;
    private Long threadId;
    private final Integer cacheSize = 10;

    LRUCache executorUpdateCache;
    LRUCache executorQueryCache;

    public MySQLConnection(SocketChannel channel, Long threadId) {
        this.channel = channel;
        this.threadId = threadId;
    }

    public MysqlUpdateExecutor getUpdateExecutor() {
        Class<?> aClass = null;
        try {
            aClass = Class.forName(MysqlUpdateExecutor.class.getName());
            Constructor<?> constructor = aClass.getDeclaredConstructor(MySQLConnection.class);
            constructor.setAccessible(true);
            Object o = constructor.newInstance(this);
            return (MysqlUpdateExecutor) o;
        } catch (Exception e) {
            throw new XException(String.format("获取更新执行器失败 %s", e.getMessage()));
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
        } catch (Exception e) {
            throw new XException(String.format("获取查询执行器失败 %s", e.getMessage()));
        }
    }

    public SocketChannel getChannel() {
        return channel;
    }

    public void setChannel(SocketChannel channel) {
        this.channel = channel;
    }


    @Override
    protected void doStart() {
        if (channel.isConnected()) {
            System.out.println("connection 启动成功");
        } else {
            throw new LifeCycleException("channel not open");
        }
    }

    @Override
    protected void doStop() {
        if (channel != null) {
            channel.close();
        }
    }

    public Long getThreadId() {
        return threadId;
    }

    public void setThreadId(Long threadId) {
        this.threadId = threadId;
    }
}
