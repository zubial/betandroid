package net.zubial.msprotocol.helpers;

import net.zubial.msprotocol.enums.MspDirectionEnum;

import java.nio.ByteBuffer;

public final class MspProtocolUtils {

    private MspProtocolUtils() {
        // Private const
    }

    public static byte[] getMspHeap(MspDirectionEnum direction) {
        byte[] result = new byte[3];
        result[0] = "$".getBytes()[0];
        result[1] = "M".getBytes()[0];
        result[2] = direction.getValue();
        return result;
    }

    public static Boolean bitCheck(int mask, int index) {
        return ((mask >> index) % 2 != 0);
    }

    public static int bitSetEnable(int mask, int index) {
        return mask | 1 << index;
    }

    public static int bitSetDisable(int mask, int index) {
        return mask & ~(1 << index);
    }

    public static byte[] concat(byte[] firstArray, byte[] secondArray) {
        int newLenght = 0;
        if (firstArray != null) {
            newLenght = firstArray.length;
        }
        if (secondArray != null) {
            newLenght += secondArray.length;
        }

        byte[] returnArray = new byte[newLenght];
        int j = 0;
        if (firstArray != null) {
            for (int i = 0; i < firstArray.length; i++) {
                returnArray[j++] = firstArray[i];
            }
        }
        if (secondArray != null) {
            for (int x = 0; x < secondArray.length; x++) {
                returnArray[j++] = secondArray[x];
            }
        }

        return returnArray;
    }

    public static byte[] addByte(byte[] array, byte newByte) {
        int newLenght = 0;
        if (array != null) {
            newLenght = array.length;
        }
        newLenght++;

        byte[] returnArray = new byte[newLenght];
        int j = 0;
        if (array != null) {
            for (int i = 0; i < array.length; i++) {
                returnArray[j++] = array[i];
            }
        }
        returnArray[j] = newByte;

        return returnArray;
    }

    public static byte[] cleanByte(byte[] array, int newLenght) {
        byte[] returnArray = new byte[newLenght];
        if (array != null) {
            for (int i = 0; i < newLenght; i++) {
                returnArray[i] = array[i];
            }
        }
        return returnArray;
    }

    public static String toHexString(ByteBuffer array) {
        String toString = "";
        for (int i = 0; i < array.limit(); i++) {
            toString += "0x" + Integer.toHexString(array.get(i) & 0xff) + " ";
        }
        return toString;
    }

    public static String toHexString(byte[] array) {
        String toString = "";
        for (int i = 0; i < array.length; i++) {
            toString += "0x" + Integer.toHexString(array[i] & 0xff) + " ";
        }
        return toString;
    }
}
