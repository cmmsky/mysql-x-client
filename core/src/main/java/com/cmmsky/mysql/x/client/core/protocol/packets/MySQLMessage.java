package com.cmmsky.mysql.x.client.core.protocol.packets;

import java.util.Arrays;

/**
 * @Author: cmmsky
 * @Date: Created in 11:13 2021/4/12
 * @Description:
 * @Modified by:
 */
public class MySQLMessage {

    private static final long NULL_LENGTH = -1;
    private static final byte[] EMPTY_BYTES = new byte[0];

    private final byte[] data;
    private final int length;
    private int position;

    public int getPosition() {
        return position;
    }

    public int getLength() {
        return length;
    }

    public MySQLMessage(byte[] data) {
        this.data = data;
        this.length = data.length;
        this.position = 0;
    }

    public long available() {
        return length - position;
    }


    public int position() {
        return position;
    }

    public void move(int i) {
        position += i;
    }
    public boolean hasRemaining() {
        return length > position;
    }

    public byte read(int i) {
        return data[i];
    }


    public byte read() {
        return data[position++];
    }


    public int readUB2() {
        final byte[] b = this.data;
        int i = b[position++] & 0xff;
        i |= (b[position++] & 0xff) << 8;
        return i;
    }

    public int readUB3() {
        final byte[] b = this.data;
        int i = b[position++] & 0xff;
        i |= (b[position++] & 0xff) << 8;
        i |= (b[position++] & 0xff) << 16;
        return i;
    }

    public long readUB4() {
        final byte[] b = this.data;
        long l = (long) (b[position++] & 0xff);
        l |= (long) (b[position++] & 0xff) << 8;
        l |= (long) (b[position++] & 0xff) << 16;
        l |= (long) (b[position++] & 0xff) << 24;
        return l;
    }

    public long readLong() {
        final byte[] b = this.data;
        long l = (long) (b[position++] & 0xff);
        l |= (long) (b[position++] & 0xff) << 8;
        l |= (long) (b[position++] & 0xff) << 16;
        l |= (long) (b[position++] & 0xff) << 24;
        l |= (long) (b[position++] & 0xff) << 32;
        l |= (long) (b[position++] & 0xff) << 40;
        l |= (long) (b[position++] & 0xff) << 48;
        l |= (long) (b[position++] & 0xff) << 56;
        return l;
    }

    // 也就是 Length Coded Binary，其数据长度不固定，长度值由数据前的 1-9 个字节决定，其中长度值所占的字节数不定，字节数由第 1 个字节决定，如下：

    /**
     *
     * 第一个字节值    后续字节数  长度值说明
     *     0-250            0   第一个字节值即为数据的真实长度
     *       251            0   空数据，数据的真实长度为零
     *       252            2   后续额外2个字节标识了数据的真实长度
     *       253            3   后续额外3个字节标识了数据的真实长度
     *       254            8   后续额外8个字节标识了数据的真实长度
     * @return
     */
    public long readLength() {
        int length = data[position++] & 0xff;
        switch (length) {
            case 251:
                return NULL_LENGTH;
            case 252:
                return readUB2();
            case 253:
                return readUB3();
            case 254:
                return readLong();
            default:
                return length;
        }
    }

    public byte[] readBytes(int length) {
        byte[] ab = new byte[length];
        System.arraycopy(data, position, ab, 0, length);
        position += length;
        return ab;
    }

    public byte[] readBytes() {
        if (position > length) {
            return EMPTY_BYTES;
        }

        byte[] ab = new byte[length - position];
        System.arraycopy(data, position, ab, 0, ab.length);
        position = length;
        return ab;
    }


    /**
     * 根据是否以 NULL 结尾，分为了有两种形式：
     *
     * 以 NULL 结尾，Null-Terminated String
     * 字符串长度不固定，当遇到 'NULL'(0x00) 字符时结束。
     * 长度编码，Length Coded String
     * 字符串长度不固定，无 'NULL'(0x00) 结束符，编码方式与上面的二进制数据相同。
     * @return
     */
    public byte[]  readBytesWithNull() {
        final byte[] b = this.data;

        if (position >= length) {
            return EMPTY_BYTES;
        }

        int offset = -1;
        for (int i = position; i < length; i++) {
            if (b[i] == 0) {
                offset = i;
                break;
            }
        }

        switch (offset) {
            case -1:
                byte[] ab1 = new byte[length - position];
                System.arraycopy(b, position, ab1, 0, ab1.length);
                position = length;
                return ab1;
            case 0:
                position++;
                return EMPTY_BYTES;
            default:
                byte[] ab2 = new byte[offset - position];
                System.arraycopy(b, position, ab2, 0, ab2.length);
                position = offset + 1;
                return ab2;
        }


    }

    public byte[] readBytesWithLength() {
        int length = (int) readLength();
        if (length <= 0) {
            return EMPTY_BYTES;
        }
        byte[] ab = new byte[length];
        System.arraycopy(data, position, ab, 0, ab.length);
        position += length;
        return ab;
    }

    public byte[] readBytesWithCrc32() {
        if (position >= length - 4) {
            return null;
        }
        byte[] bytes = readBytes(length - position - 4);
        position = length;
        return bytes;

    }

    public String toString() {
        return new StringBuilder().append(Arrays.toString(data)).toString();
    }


}
