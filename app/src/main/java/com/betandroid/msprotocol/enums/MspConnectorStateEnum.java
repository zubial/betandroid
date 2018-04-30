package com.betandroid.msprotocol.enums;

public enum MspConnectorStateEnum {

    STATE_NONE (0, "No connection"),
    STATE_CONNECTING(1, "Connecting"),
    STATE_CONNECTED (2, "Connected"),
    STATE_WAITING (3, "Waiting activity"),
    STATE_DISCONNECTED (4, "Disconnected"),
    STATE_UNAVAILABLE (5, "Connection unavailable"),
    STATE_RECEIVING (6, "Receiving data"),
    STATE_WRITING (7, "Writing data");


    private final Integer code;
    private final String label;

    MspConnectorStateEnum(final Integer code, final String label) {
        this.code = code;
        this.label = label;
    }

    public Integer getCode() {
        return code;
    }

    public String getLabel() {
        return label;
    }

    public static MspConnectorStateEnum findByCode(final Integer s) {
        for (final MspConnectorStateEnum e : MspConnectorStateEnum.values()) {
            if (e.getCode() == s) {
                return e;
            }
        }
        return null;
    }
}
