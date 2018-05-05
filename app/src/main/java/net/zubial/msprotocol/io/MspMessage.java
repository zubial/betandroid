package net.zubial.msprotocol.io;

import net.zubial.msprotocol.enums.MspDirectionEnum;
import net.zubial.msprotocol.enums.MspMessageTypeEnum;

import java.io.Serializable;

public class MspMessage implements Serializable {

    private static final long serialVersionUID = 1L;

    private MspDirectionEnum direction;
    private int size;
    private MspMessageTypeEnum messageType;
    private byte[] payload;
    private byte[] nextMessage;
    private int readIndex = 0;
    private Boolean isLoad = false;

    public MspMessage() {
        // Default Constructor
    }

    public MspDirectionEnum getDirection() {
        return direction;
    }

    public void setDirection(MspDirectionEnum direction) {
        this.direction = direction;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public MspMessageTypeEnum getMessageType() {
        return messageType;
    }

    public void setMessageType(MspMessageTypeEnum messageType) {
        this.messageType = messageType;
    }

    public byte[] getPayload() {
        return payload;
    }

    public void setPayload(byte[] payload) {
        this.payload = payload;
    }

    public byte[] getNextMessage() {
        return nextMessage;
    }

    public void setNextMessage(byte[] nextMessage) {
        this.nextMessage = nextMessage;
    }

    public Boolean isLoad() {
        return isLoad;
    }

    public void isLoad(Boolean load) {
        isLoad = load;
    }

    public void resetReader() {
        readIndex = 0;
    }

    public int readInt32() {
        return readInt16() + readInt16() * 65536;
    }

    public int readUInt32() {
        return readUInt16() + readUInt16() * 65536;
    }

    public int readInt16() {
        return readInt8() + readInt8() * 256;
    }

    public int readUInt16() {
        return readUInt8() + readUInt8() * 256;
    }

    public int readUInt8() {
        return payload[readIndex++] & 0xff;
    }

    public int readInt8() {
        return payload[readIndex++];
    }

    public String readString() {
        return new String(payload, 0, payload.length - readIndex);
    }

    public String readString(int length) {
        readIndex += length;
        return new String(payload, readIndex - length, length);
    }
}
