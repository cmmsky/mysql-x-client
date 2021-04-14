package com.cmmsky.mysql.x.client.core.protocol.socket;

import java.io.*;
import java.net.Socket;

/**
 * @Author: cmmsky
 * @Date: Created in 17:38 2021/4/9
 * @Description:
 * @Modified by:
 */
public class BioSocketChannel implements SocketChannel {

    private static final int INPUT_STREAM_BUFFER = 1024;
    private static final int OUTPUT_STREAM_BUFFER = 1024;

    private Socket socket;
    private InputStream in;
    private OutputStream out;
    private boolean isConnected;


    public BioSocketChannel(Socket socket) throws IOException {
        this.socket = socket;
        this.in = new BufferedInputStream(socket.getInputStream(), INPUT_STREAM_BUFFER);
        // this.out = new BufferedOutputStream(socket.getOutputStream(), OUTPUT_STREAM_BUFFER);
        this.out = socket.getOutputStream();
    }



    public void write(byte[] bytes) throws IOException {
        out.write(bytes);
    }

    public void write(int b) throws IOException {
        out.write(b);
    }

    public void read(byte[] data, int off, int len) throws IOException {
        for (int got = 0; len > 0;) {
            got = in.read(data, off, len);
            if (got < 0) {
                throw new EOFException();
            }
            off += got;
            len -= got;
        }
    }


    public byte read() throws IOException {
        int got = in.read();
        if (got < 0) {
            throw new EOFException();
        }
        return (byte)(got & 0xff);
    }

    public boolean isConnected() {
        return isConnected;
    }

    public void close() {

        if (in != null) {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (out != null) {
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }
}
