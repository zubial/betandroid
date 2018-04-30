package net.zubial.msprotocol.enums;

public enum MspFlightControllerEnum {

    MULTIWII("MWII"),
    BASEFLIGHT("BAFL"),
    BETAFLIGHT("BTFL"),
    CLEANFLIGHT("CLFL"),
    INAV("INAV"),
    RACEFLIGHT("RCFL");

    private final String code;

    MspFlightControllerEnum(final String code) {
        this.code = code;
    }

    public static MspFlightControllerEnum findByCode(final String code) {
        for (final MspFlightControllerEnum e : MspFlightControllerEnum.values()) {
            if (e.getCode().equals(code)) {
                return e;
            }
        }
        return null;
    }

    public String getCode() {
        return code;
    }
}
