package com.cmmsky.mysql.x.client.core.utils;

/**
 * @Author: dongguapi
 * @Date: Created in 20:18 2021/4/15
 * @Description:
 * @Modified by:
 */
public class LifeCycleException extends RuntimeException {

    public LifeCycleException() {
        super();
    }

    public LifeCycleException(String message, Throwable cause) {
        super(message, cause);
    }

    public LifeCycleException(String message) {
        super(message);
    }

    public LifeCycleException(Throwable cause) {
        super(cause);
    }
}
