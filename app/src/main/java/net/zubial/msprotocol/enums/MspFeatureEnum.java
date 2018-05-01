package net.zubial.msprotocol.enums;

public enum MspFeatureEnum {

    FEATURE_RX_PPM(0, "", false),
    FEATURE_INFLIGHT_ACC_CAL(2, "", true),
    FEATURE_RX_SERIAL(3, "", false),
    FEATURE_MOTOR_STOP(4, "", true),
    FEATURE_SERVO_TILT(5, "", true),
    FEATURE_SOFTSERIAL(6, "", true),
    FEATURE_GPS(7, "", true),
    FEATURE_SONAR(9, "", true),
    FEATURE_TELEMETRY(10, "", true),
    FEATURE_3D(12, "", true),
    FEATURE_RX_PARALLEL_PWM(13, "", false),
    FEATURE_RX_MSP(14, "", false),
    FEATURE_RSSI_ADC(15, "", true),
    FEATURE_LED_STRIP(16, "", true),
    FEATURE_DISPLAY(17, "", true),
    FEATURE_BLACKBOX(19, "", true),
    FEATURE_CHANNEL_FORWARDING(20, "", true),
    FEATURE_FAILSAFE(8, "", true),
    FEATURE_TRANSPONDER(21, "", true),
    FEATURE_AIRMODE(22, "", true),
    FEATURE_SUPEREXPO_RATES(23, "", true),
    FEATURE_SDCARD(23, "", true),
    FEATURE_OSD(18, "", true),
    FEATURE_VTX(24, "", true),
    FEATURE_RX_SPI(25, "", false),
    FEATURE_ESC_SENSOR(27, "", true),
    FEATURE_ANTI_GRAVITY(28, "", true),
    FEATURE_DYNAMIC_FILTER(29, "", true),
    FEATURE_VBAT(1, "", true),
    FEATURE_CURRENT_METER(11, "", true);

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
