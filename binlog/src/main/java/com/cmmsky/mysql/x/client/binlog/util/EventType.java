package com.cmmsky.mysql.x.client.binlog.util;

/**
 * @Author: cmmsky
 * @Date: Created in 14:37 2021/4/13
 * @Description:
 * @Modified by:
 */
public interface EventType {

    public final byte UNKNOWN_EVENT = 0x00;

    public final byte START_EVENT_V3 = 0x01;


    // 0x02
    public final byte QUERY_EVENT = 0x02;

    // 0x03
    public final byte STOP_EVENT = 0x03;

    // 0x04
    public final byte ROTATE_EVENT = 0x04;

    // 0x05
    public final byte INTVAR_EVENT = 0x05;

    // 0x06
    public final byte LOAD_EVENT = 0x06;
    // 0x07
    public final byte SLAVE_EVENT = 0x07;
    // 0x08
    public final byte CREATE_FILE_EVENT = 0x08;

    // 0x09
    public final byte APPEND_BLOCK_EVENT = 0x09;

    // 0x0a
    public final byte EXEC_LOAD_EVENT = 0x0a;

    // 0x0b
    public final byte DELETE_FILE_EVENT = 0x0b;

    //  0x0c
    public final byte NEW_LOAD_EVENT = 0x0c;

    // 0x0d
    public final byte RAND_EVENT = 0x0d;

    // 0x0e
    public final byte USER_VAR_EVENT = 0x0e;

    // 0x0f
    public final byte FORMAT_DESCRIPTION_EVENT = 0x0f;

    // 0x10
    public final byte XID_EVENT = 0x10;

    // 0x11
    public final byte BEGIN_LOAD_QUERY_EVENT = 0x11;

    // 0x12
    public final byte EXECUTE_LOAD_QUERY_EVENT = 0x12;

    // 0x13 19
    public final byte TABLE_MAP_EVENT = 0x13;

    // 0x14
    public final byte WRITE_ROWS_EVENTv0 = 0x14;

    // 0x15
    public final byte UPDATE_ROWS_EVENTv0 = 0x15;

    // 0x16
    public final byte DELETE_ROWS_EVENTv0 = 0x16;

    // 0x17
    public final byte WRITE_ROWS_EVENTv1 = 0x17;

    // 0x18
    public final byte UPDATE_ROWS_EVENTv1 = 0x18;

    // 0x19
    public final byte DELETE_ROWS_EVENTv1 = 0x19;

    // 0x1a
    public final byte INCIDENT_EVENT = 0x1a;

    // 0x1b 27
    public final byte HEARTBEAT_EVENT = 0x1b;

    // 0x1c
    public final byte IGNORABLE_EVENT = 0x1c;

    // 0x1d
    public final byte ROWS_QUERY_EVENT = 0x1d;

    // 0x1e
    public final byte WRITE_ROWS_EVENTv2 = 0x1e;

    // 0x1f
    public final byte UPDATE_ROWS_EVENTv2 = 0x1f;

    // 0x20
    public final byte DELETE_ROWS_EVENTv2 = 0x20;

    // 0x21
    public final byte GTID_EVENT = 0x21;

    // 0x22 34
    public final byte ANONYMOUS_GTID_EVENT = 0x22;

    // 0x23 35
    public final byte PREVIOUS_GTIDS_EVENT = 0x23;
}
