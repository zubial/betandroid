package net.zubial.msprotocol.io;

import android.util.Log;

import net.zubial.msprotocol.enums.MspDirectionEnum;
import net.zubial.msprotocol.enums.MspErrorCodeEnum;
import net.zubial.msprotocol.enums.MspMessageTypeEnum;
import net.zubial.msprotocol.exceptions.MspBaseException;
import net.zubial.msprotocol.exceptions.MspProtocolExceptionMsp;
import net.zubial.msprotocol.helpers.MspProtocolUtils;

import java.nio.ByteBuffer;

public final class MspDecoder {

    private static final String TAG = "MspDecoder";

    private static final byte HEADER_$ = (byte) '$';
    private static final byte HEADER_M = (byte) 'M';

    private static final int STEP_INIT = 0;
    private static final int STEP_PROTOCOL = 1;
    private static final int STEP_DIRECTION = 2;
    private static final int STEP_SIZE = 3;
    private static final int STEP_TYPE = 4;
    private static final int STEP_DATA = 5;
    private static final int STEP_END = 6;

    public static MspMessage decode(ByteBuffer inBuffer) throws MspBaseException {
        int nextStep = STEP_INIT;
        byte[] message = inBuffer.array();
        byte[] nextMessage = null;
        byte checksum = 0;
        int index = 0;

        byte[] payload = new byte[1];

        MspMessage inMessage = new MspMessage();

        for (byte c : message) {
            switch (nextStep) {
                case STEP_INIT:
                    if (HEADER_$ == c) {
                        nextStep = STEP_PROTOCOL;
                    }
                    break;

                case STEP_PROTOCOL:
                    if (HEADER_M == c) {
                        nextStep = STEP_DIRECTION;
                    }
                    break;

                case STEP_DIRECTION:
                    inMessage.setDirection(MspDirectionEnum.findByValue(c));
                    nextStep = STEP_SIZE;
                    break;

                case STEP_SIZE:
                    inMessage.setSize(c & 0xFF);
                    checksum ^= (c & 0xFF);

                    if ((c & 0xFF) > 0) {
                        payload = new byte[(c & 0xFF)];
                    }

                    nextStep = STEP_TYPE;

                    break;

                case STEP_TYPE:
                    inMessage.setMessageType(MspMessageTypeEnum.findByValue(c));
                    checksum ^= (c & 0xFF);
                    nextStep = STEP_DATA;

                    break;

                case STEP_DATA:
                    if (index < inMessage.getSize()) {
                        payload[index++] = c;
                        checksum ^= (c & 0xFF);
                    } else {
                        nextMessage = MspProtocolUtils.addByte(nextMessage, c);

                        if ((checksum & 0xFF) != (c & 0xFF)) {
                            throw new MspProtocolExceptionMsp(MspErrorCodeEnum.MSP_PROTOCOL_CRC_MISMATCH);
                        }

                        inMessage.setPayload(payload);
                        inMessage.isLoad(true);

                        Log.d(TAG, "Receive : " + inMessage.getMessageType().name() + " - " + MspProtocolUtils.toHexString(payload));

                        nextStep = STEP_END;
                    }
                    break;

                case STEP_END:
                    nextMessage = MspProtocolUtils.addByte(nextMessage, c);
                    break;

            }
        }

        if (nextStep == STEP_END) {
            inMessage.setNextMessage(nextMessage);
        } else {
            inMessage.setNextMessage(message);
        }

        return inMessage;
    }
}
