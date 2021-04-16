package com.cmmsky.mysql.x.client.core.utils;

/**
 * @Author: dongguapi
 * @Date: Created in 20:25 2021/4/15
 * @Description:
 * @Modified by:
 */
public class XException extends RuntimeException {

    public XException() {
        super();
    }

    public XException(String message, Throwable cause) {
        super(message, cause);
    }

    public XException(String message) {
        super(message);
    }

    public XException(Throwable cause) {
        super(cause);
    }
}
