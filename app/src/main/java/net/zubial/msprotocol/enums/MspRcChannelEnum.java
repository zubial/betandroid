package net.zubial.msprotocol.enums;

public enum MspRcChannelEnum {

    CHANNEL_ROLL(0, "Roll"),
    CHANNEL_PITCH(1, "Pitch"),
    CHANNEL_YAW(2, "Yaw"),
    CHANNEL_TROTTLE(3, "Trottle"),
    CHANNEL_AUX1(4, "Aux 1"),
    CHANNEL_AUX2(5, "Aux 2"),
    CHANNEL_AUX3(6, "Aux 3"),
    CHANNEL_AUX4(7, "Aux 4"),
    CHANNEL_AUX5(8, "Aux 5"),
    CHANNEL_AUX6(9, "Aux 6"),
    CHANNEL_AUX7(10, "Aux 7"),
    CHANNEL_AUX8(11, "Aux 8"),
    CHANNEL_AUX9(12, "Aux 9"),
    CHANNEL_AUX10(13, "Aux 10"),
    CHANNEL_AUX11(14, "Aux 11"),
    CHANNEL_AUX12(15, "Aux 12");


    private final Integer code;
    private final String label;

    MspRcChannelEnum(final Integer code, final String label) {
        this.code = code;
        this.label = label;
    }

    public static MspRcChannelEnum findByCode(final Integer s) {
        for (final MspRcChannelEnum e : MspRcChannelEnum.values()) {
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
