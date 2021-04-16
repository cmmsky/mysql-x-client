package com.cmmsky.mysql.x.client.binlog;

/**
 * @Author: dongguapi
 * @Date: Created in 20:09 2021/4/15
 * @Description:
 * @Modified by:
 */
public class BinlogDumpController {

    public static void main(String[] args) {
        final BinlogDumpServer binlogDumpServer = new BinlogDumpServer();
        new Thread(new Runnable() {
            @Override
            public void run() {
                binlogDumpServer.start();

            }
        }).start();

        try {
            Thread.sleep(20 * 1000);
            binlogDumpServer.stop();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
