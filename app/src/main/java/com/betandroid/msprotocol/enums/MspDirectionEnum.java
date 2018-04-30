package com.betandroid.msprotocol.enums;

public enum MspDirectionEnum {

    MSP_INCOMING(">", "Incoming message", (byte)'>'),
    MSP_OUTBOUND("<", "Outbound message", (byte)'<'),

    MSP_UNKNOWN("!", "Unknown direction", (byte)'!');

    private final String code;
    private final String label;
    private final byte value;

    MspDirectionEnum(final String code, final String label, final byte value) {
        this.code = code;
        this.label = label;
        this.value = value;
    }

    public String getCode() {
        return code;
    }

    public String getLabel() {
        return label;
    }

    public byte getValue() {
        return value;
    }

    public static MspDirectionEnum findByValue(final byte s) {
        for (final MspDirectionEnum e : MspDirectionEnum.values()) {
            if (e.getValue() == s) {
                return e;
            }
        }
        return MSP_UNKNOWN;
    }
}
