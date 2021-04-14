package com.cmmsky.mysql.x.client.binlog;

/**
 * @Author: cmmsky
 * @Date: Created in 17:32 2021/4/12
 * @Description:
 * @Modified by:
 */
public class TestBinlogDumpServer {

    public static void main(String[] args) {
        BinlogDumpServer binlogDumpServer = new BinlogDumpServer();
        binlogDumpServer.start();
    }
}
