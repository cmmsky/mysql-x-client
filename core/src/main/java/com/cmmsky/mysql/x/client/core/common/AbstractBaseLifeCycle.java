package com.cmmsky.mysql.x.client.core.common;

/**
 * @Author: dongguapi
 * @Date: Created in 19:43 2021/4/15
 * @Description:
 * @Modified by:
 */
public abstract class AbstractBaseLifeCycle implements BaseLifeCycle {

    protected volatile boolean running = false;

    @Override
    public void start() {
        this.running = true;
        doStart();
    }

    protected abstract void doStart();

    @Override
    public void stop() {
        this.running = false;
        doStop();
    }

    @Override
    public boolean isStart() {
        return running;
    }

    protected abstract void doStop();

}
