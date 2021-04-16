package com.cmmsky.mysql.x.client.binlog.packet;

import com.cmmsky.mysql.x.client.core.protocol.packets.MySQLMessage;

/**
 * @Author: cmmsky
 * @Date: Created in 14:14 2021/4/9
 * @Description: 支持XA的存储引擎才有，本地测试的数据库存储引擎是innodb，所有上面出现了XID_EVENT；innodb事务提交产生了QUERY_EVENT的BEGIN声明，QUERY_EVENT以及COMMIT声明，
 * 如果是myIsam存储引擎也会有BEGIN和COMMIT声明，只是COMMIT类型不是XID_EVENT；
 * @Modified by:
 */
public class XidEventData implements EventData {

    private long xid;

    @Override
    public void read(byte[] data) {
        MySQLMessage mm = new MySQLMessage(data);
        xid = mm.readLong();
    }

    @Override
    public String toString() {
        return "XidEventData{" +
                "xid=" + xid +
                '}';
    }

    public long getXid() {
        return xid;
    }

    public void setXid(long xid) {
        this.xid = xid;
    }
}
