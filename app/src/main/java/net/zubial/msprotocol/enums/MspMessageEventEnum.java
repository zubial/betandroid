package net.zubial.msprotocol.enums;

public enum MspMessageEventEnum {

    EVENT_MSP_DONE(1),
    EVENT_MSP_UPDATED(2),

    EVENT_MSP_DATA(3),
    EVENT_MSP_SYSTEM_DATA(4),
    EVENT_MSP_FEATURE_DATA(5),
    EVENT_MSP_LIVE_DATA(6),
    EVENT_MSP_BATTERY_DATA(7),
    EVENT_MSP_MODES_DATA(8),

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
