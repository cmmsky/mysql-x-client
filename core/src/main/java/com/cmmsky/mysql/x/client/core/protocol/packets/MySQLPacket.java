package com.cmmsky.mysql.x.client.core.protocol.packets;

/**
 * @Author: cmmsky
 * @Date: Created in 16:57 2021/4/9
 * @Description: mysql 通讯包base，前三字节为body长度，后一个字节为序号
 *  |--->3<---|->1<-|--------------->body<----------------|
 * @Modified by:
 */
public abstract class MySQLPacket {


    protected int packetLength;
    protected byte packetId;



    // 0x00    COM_SLEEP   （内部线程状态)
    public static final byte COM_SLEEP = 0;

    // 0x01    COM_QUIT    关闭连接
    public static final byte COM_QUIT = 1;

    // 0x02    COM_INIT_DB 切换数据库
    public static final byte COM_INIT_DB = 2;

    // 0x03    COM_QUERY   SQL查询请求
    public static final byte COM_QUERY = 3;

    /**
     * mysql_list_fields
     * 0x04    COM_FIELD_LIST  获取数据表字段信息
     */
    public static final byte COM_FIELD_LIST = 4;

    /**
     * mysql_create_db (deprecated)
     * 0x05    COM_CREATE_DB   创建数据库
     */
    public static final byte COM_CREATE_DB = 5;

    /**
     * mysql_drop_db (deprecated)
     * 0x06    COM_DROP_DB 删除数据库
     */
    public static final byte COM_DROP_DB = 6;

    /**
     * mysql_refresh
     * 0x07    COM_REFRESH 清除缓存
     */
    public static final byte COM_REFRESH = 7;

    /**
     * mysql_shutdown
     * 0x08    COM_SHUTDOWN    停止服务器
     */
    public static final byte COM_SHUTDOWN = 8;

    /**
     * mysql_stat
     * 0x09    COM_STATISTICS  获取服务器统计信息
     */
    public static final byte COM_STATISTICS = 9;

    /**
     * mysql_list_processes
     * 0x0A    COM_PROCESS_INFO    获取当前连接的列表
     */
    public static final byte COM_PROCESS_INFO = 10;

    /**
     * none, this is an internal thread state
     * 0x0B    COM_CONNECT （内部线程状态)
     */
    public static final byte COM_CONNECT = 11;

    /**
     * mysql_kill
     * 0x0C    COM_PROCESS_KILL    中断某个连接
     */
    public static final byte COM_PROCESS_KILL = 12;

    /**
     * mysql_dump_debug_info
     * 0x0D    COM_DEBUG   保存服务器调试信息
     */
    public static final byte COM_DEBUG = 13;

    /**
     * mysql_ping
     * 0x0E    COM_PING    测试连通性
     */
    public static final byte z = 14;

    /**
     * none, this is an internal thread state
     * 0x0F    COM_TIME    （内部线程状态）
     */
    public static final byte COM_TIME = 15;

    /**
     * none, this is an internal thread state
     * 0x10    COM_DELAYED_INSERT  （内部线程状态）
     */
    public static final byte COM_DELAYED_INSERT = 16;

    /**
     * mysql_change_user
     * 0x11    COM_CHANGE_USER 重新登陆（不断连接）
     */
    public static final byte COM_CHANGE_USER = 17;

    /**
     * used by slave server mysqlbinlog
     * 0x12    COM_BINLOG_DUMP 获取二进制日志信息
     */
    public static final byte COM_BINLOG_DUMP = 18;

    /**
     * used by slave server to get master table
     * 0x13    COM_TABLE_DUMP  获取数据表结构信息
     */
    public static final byte COM_TABLE_DUMP = 19;

    /**
     * used by slave to log connection to master
     * 0x14    COM_CONNECT_OUT （内部线程状态)
     */
    public static final byte COM_CONNECT_OUT = 20;

    /**
     * used by slave to register to master
     * 0x15    COM_REGISTER_SLAVE  从服务器向主服务器进行注册
     */
    public static final byte COM_REGISTER_SLAVE = 21;

    /**
     * mysql_stmt_prepare
     * 0x16    COM_STMT_PREPARE    预处理SQL语句
     */
    public static final byte COM_STMT_PREPARE = 22;

    /**
     * mysql_stmt_execute
     * 0x17    COM_STMT_EXECUTE    执行预处理语句
     */
    public static final byte COM_STMT_EXECUTE = 23;

    /**
     * mysql_stmt_send_long_data
     * 0x18    COM_STMT_SEND_LONG_DATA 发送BLOB类型的数据
     */
    public static final byte COM_STMT_SEND_LONG_DATA = 24;

    /**
     * mysql_stmt_close
     * 0x19    COM_STMT_CLOSE  销毁预处理语句
     */
    public static final byte COM_STMT_CLOSE = 25;

    /**
     * mysql_stmt_reset
     * 0x1A    COM_STMT_RESET  清除预处理语句参数缓存
     */
    public static final byte COM_STMT_RESET = 26;

    /**
     * mysql_set_server_option
     * 0x1B    COM_SET_OPTION  设置语句选项
     */
    public static final byte COM_SET_OPTION = 27;

    /**
     * mysql_stmt_fetch
     * 0x1C    COM_STMT_FETCH  获取预处理语句的执行结果
     */
    public static final byte COM_STMT_FETCH = 28;

    /**
     * cobar heartbeat
     */
    public static final byte COM_HEARTBEAT = 64;



    public String getPacketInfo() {
        return this.getClass().getSimpleName();
    }


}
