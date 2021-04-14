package com.cmmsky.mysql.x.client.core.utils;

/**
 * @Author: cmmsky
 * @Date: Created in 14:44 2021/4/12
 * @Description:
 * @Modified by:
 */
public class UnknownPacketException extends RuntimeException {



    public UnknownPacketException() {
        super();
    }

    public UnknownPacketException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnknownPacketException(String message) {
        super(message);
    }

    public UnknownPacketException(Throwable cause) {
        super(cause);
    }
}
