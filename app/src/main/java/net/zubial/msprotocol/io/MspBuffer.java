package net.zubial.msprotocol.io;

import java.nio.ByteBuffer;

public final class MspBuffer {

    private ByteBuffer buffer = null;

    public MspBuffer(int size) {
        buffer = ByteBuffer.allocate(size);
    }

    public void writeString(String value) {
        buffer.put(value.getBytes());
    }

    public void writeInt32(int value) {
        buffer.put((byte) (0xFF & value));
        buffer.put((byte) (0xFF & (value >> 8)));
        buffer.put((byte) (0xFF & (value >> 16)));
        buffer.put((byte) (0xFF & (value >> 24)));
    }

    public void writeInt16(int value) {
        buffer.put((byte) (0xFF & value));
        buffer.put((byte) (0xFF & (value >> 8)));
    }

    public void writeInt8(int value) {
        buffer.put((byte) (0xFF & value));
    }

    public int getSize() {
        return buffer.limit();
    }

    public byte[] getMessage() {
        buffer.position(0);
        return buffer.array();
    }
}
