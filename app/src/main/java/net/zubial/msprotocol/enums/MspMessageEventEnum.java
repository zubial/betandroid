package net.zubial.msprotocol.enums;

public enum MspMessageEventEnum {

    EVENT_MSP_DATA(1),
    EVENT_MSP_SYSTEM_DATA(2),
    EVENT_MSP_FEATURE_DATA(3),
    EVENT_MSP_LIVE_DATA(4),
    EVENT_MSP_BATTERY_DATA(4),

    MSP_UNKNOW(0);

    private final int code;

    MspMessageEventEnum(final int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public Boolean isEqual(MspMessageEventEnum compare) {
        return (this.getCode() == compare.getCode());
    }
}
