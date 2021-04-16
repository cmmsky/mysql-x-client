package com.cmmsky.mysql.x.client.core.common;

/**
 * @Author: dongguapi
 * @Date: Created in 19:40 2021/4/15
 * @Description: 基础生命周期
 * @Modified by:
 */
public interface BaseLifeCycle {

    /**
     * 开始生命周期
     * @throws Exception
     */
    void start() throws Exception;

    /**
     * 结束生命周期
     */
    public void stop();

    /**
     * 是否启动了生命周期
     * @return
     */
    public boolean isStart();
}
