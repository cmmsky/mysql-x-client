/*
 * Copyright 1999-2012 Alibaba Group.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.cmmsky.mysql.x.client.core.utils;


import com.cmmsky.mysql.x.client.core.protocol.packets.BinaryPacket;
import com.cmmsky.mysql.x.client.core.protocol.packets.ErrorPacket;

public class ErrorPacketException extends RuntimeException {

    public ErrorPacketException() {
        super();
    }

    public ErrorPacketException(String message, Throwable cause) {
        super(message, cause);
    }

    public ErrorPacketException(String message) {
        super(message);
    }

    public ErrorPacketException(Throwable cause) {
        super(cause);
    }

    public static void handleFailure(BinaryPacket bin) {
        ErrorPacket errorPacket = new ErrorPacket();
        errorPacket.read(bin);
        throw new ErrorPacketException(new String(errorPacket.getMessage()));
    }

}
