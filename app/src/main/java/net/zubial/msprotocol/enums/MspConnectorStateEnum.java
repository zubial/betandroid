package net.zubial.msprotocol.enums;

public enum MspConnectorStateEnum {

    STATE_NONE(0, "No connection"),
    STATE_CONNECTING(1, "Connecting"),
    STATE_CONNECTED(2, "Connected"),
    STATE_HANDSHAKE(3, "Handshake"),
    STATE_WAITING(4, "Waiting activity"),
    STATE_DISCONNECTED(5, "Disconnected"),
    STATE_UNAVAILABLE(6, "Connection unavailable"),
    STATE_RECEIVING(7, "Receiving data"),
    STATE_WRITING(8, "Writing data");


    private final Integer code;
    private final String label;

    MspConnectorStateEnum(final Integer code, final String label) {
        this.code = code;
        this.label = label;
    }

    public static MspConnectorStateEnum findByCode(final Integer s) {
        for (final MspConnectorStateEnum e : MspConnectorStateEnum.values()) {
            if (e.getCode() == s) {
                return e;
            }
        }
        return null;
    }

    public Integer getCode() {
        return code;
    }

    public String getLabel() {
        return label;
    }
}
