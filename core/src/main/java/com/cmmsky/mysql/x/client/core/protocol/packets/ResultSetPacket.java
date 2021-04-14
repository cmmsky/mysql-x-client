package com.cmmsky.mysql.x.client.core.protocol.packets;

import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: cmmsky
 * @Date: Created in 16:01 2021/4/12
 * @Description:
 * @Modified by:
 */
public class ResultSetPacket extends MySQLPacket {

    private SocketAddress sourceAddress;
    private List<FieldPacket> fieldDescriptors = new ArrayList<FieldPacket>();
    private List<String>      fieldValues      = new ArrayList<String>();

    public void setFieldDescriptors(List<FieldPacket> fieldDescriptors) {
        this.fieldDescriptors = fieldDescriptors;
    }

    public List<FieldPacket> getFieldDescriptors() {
        return fieldDescriptors;
    }

    public void setFieldValues(List<String> fieldValues) {
        this.fieldValues = fieldValues;
    }

    public List<String> getFieldValues() {
        return fieldValues;
    }

    public void setSourceAddress(SocketAddress sourceAddress) {
        this.sourceAddress = sourceAddress;
    }

    public SocketAddress getSourceAddress() {
        return sourceAddress;
    }
}
