package com.cmmsky.mysql.x.client.binlog;

import com.cmmsky.mysql.x.client.core.protocol.packets.request.GtidSet;

/**
 * @Author: cmmsky
 * @Date: Created in 10:55 2021/4/13
 * @Description:
 * @Modified by:
 */
public class BinlogDumpContext {

    private final static ThreadLocal<BinlogDumpContext> contextThreadLocal = new ThreadLocal<BinlogDumpContext>();


    private ChecksumType checksumType;

    private boolean mayContainExtraInformation = true;

    private GtidSet gtidSet;



    public ChecksumType getChecksumType() {
        return checksumType;
    }

    public void setChecksumType(ChecksumType checksumType) {
        this.checksumType = checksumType;
    }


    public static BinlogDumpContext getBinlogDumpContext() {
        return contextThreadLocal.get();
    }

    public static void setBinlogDumpContext(BinlogDumpContext context) {
        contextThreadLocal.set(context);
    }

    public boolean isMayContainExtraInformation() {
        return mayContainExtraInformation;
    }

    public void setMayContainExtraInformation(boolean mayContainExtraInformation) {
        this.mayContainExtraInformation = mayContainExtraInformation;
    }

    public GtidSet getGtidSet() {
        return gtidSet;
    }

    public void setGtidSet(GtidSet gtidSet) {
        this.gtidSet = gtidSet;
    }
}
