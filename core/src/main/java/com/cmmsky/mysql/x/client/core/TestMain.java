package com.cmmsky.mysql.x.client.core;

import com.cmmsky.mysql.x.client.core.executor.MysqlQueryExecutor;

import java.io.IOException;

/**
 * @Author: cmmsky
 * @Date: Created in 17:12 2021/4/12
 * @Description:
 * @Modified by:
 */
public class TestMain {

    public static void main(String[] args) {
        MySQLDataSource mySQLDataSource = new MySQLDataSource("127.0.0.1", 3306, "root", "123456");
        try {
            MySQLConnection connection = mySQLDataSource.getConnection();
            MysqlQueryExecutor queryExecutor = connection.getQueryExecutor();
            queryExecutor.query("select 1");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
