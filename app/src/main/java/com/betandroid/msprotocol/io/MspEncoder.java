package com.betandroid.msprotocol.io;

import com.betandroid.msprotocol.enums.MspDirectionEnum;
import com.betandroid.msprotocol.enums.MspMessageTypeEnum;
import com.betandroid.msprotocol.exceptions.MspBaseException;
import com.betandroid.msprotocol.helpers.MspUtils;

public final class MspEncoder {

    public static byte[] encode(MspDirectionEnum direction, MspMessageTypeEnum messageType, byte[] payload)  throws MspBaseException {
        int size;
        if (payload != null) {
            size = payload.length;
        } else {
            size = 0;
        }

        // new Message
        byte[] message = new byte[6 + size];
        byte checksum = 0;

        // add MessageHeap
        byte[] messageHeap = MspUtils.getMspHeap(direction);
        int index = messageHeap.length;
        System.arraycopy(messageHeap, 0, message, 0, messageHeap.length);

        // add Size
        message[index++] = ((byte) (size & 0xFF));
        checksum ^= (size & 0xFF);

        // add MessageType
        message[index++] = messageType.getValue();
        checksum ^= messageType.getValue();

        // add Payload
        if (payload != null) {
            for (byte c : payload) {
                message[index++] = c;
                checksum ^= c;
            }
        }

        message[index] = checksum;

        return message;
    }
}
