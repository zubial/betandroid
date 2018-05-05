package net.zubial.msprotocol.enums;

public enum MspFeatureEnum {

    FEATURE_RX_PPM(0, "RX PPM", false),
    FEATURE_INFLIGHT_ACC_CAL(2, "INFLIGHT ACC CAL", true),
    FEATURE_RX_SERIAL(3, "RX SERIAL", false),
    FEATURE_MOTOR_STOP(4, "MOTOR STOP", true),
    FEATURE_SERVO_TILT(5, "SERVO TILT", true),
    FEATURE_SOFTSERIAL(6, "SOFTSERIAL", true),
    FEATURE_GPS(7, "GPS", true),
    FEATURE_SONAR(9, "SONAR", true),
    FEATURE_TELEMETRY(10, "TELEMETRY", true),
    FEATURE_3D(12, "3D", true),
    FEATURE_RX_PARALLEL_PWM(13, "RX PARALLEL PWM", false),
    FEATURE_RX_MSP(14, "RX MSP", false),
    FEATURE_RSSI_ADC(15, "RSSI ADC", true),
    FEATURE_LED_STRIP(16, "LED STRIP", true),
    FEATURE_DISPLAY(17, "DISPLAY", true),
    FEATURE_BLACKBOX(19, "BLACKBOX", true),
    FEATURE_CHANNEL_FORWARDING(20, "CHANNEL FORWARDING", true),
    FEATURE_FAILSAFE(8, "FAILSAFE", true),
    FEATURE_TRANSPONDER(21, "TRANSPONDER", true),
    FEATURE_AIRMODE(22, "AIRMODE", true),
    FEATURE_SUPEREXPO_RATES(23, "SUPEREXPO RATES", true),
    FEATURE_SDCARD(23, "SDCARD", true),
    FEATURE_OSD(18, "OSD", true),
    FEATURE_VTX(24, "VTX", true),
    FEATURE_RX_SPI(25, "RX SPI", false),
    FEATURE_ESC_SENSOR(27, "ESC SENSOR", true),
    FEATURE_ANTI_GRAVITY(28, "ANTI GRAVITY", true),
    FEATURE_DYNAMIC_FILTER(29, "DYNAMIC FILTER", true),
    FEATURE_VBAT(1, "VBAT", true),
    FEATURE_CURRENT_METER(11, "CURRENT METER", true);

    private final Integer code;
    private final String label;
    private final Boolean selectable;

    MspFeatureEnum(final Integer code, final String label, final Boolean selectable) {
        this.code = code;
        this.label = label;
        this.selectable = selectable;
    }

    public static MspFeatureEnum findByCode(final Integer s) {
        for (final MspFeatureEnum e : MspFeatureEnum.values()) {
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

    public Boolean isSelectable() {
        return selectable;
    }
}
