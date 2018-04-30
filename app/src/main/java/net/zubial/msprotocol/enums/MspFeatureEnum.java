package net.zubial.msprotocol.enums;

public enum MspFeatureEnum {

    FEATURE_RX_PPM(0, ""),
    FEATURE_INFLIGHT_ACC_CAL(2, ""),
    FEATURE_RX_SERIAL(3, ""),
    FEATURE_MOTOR_STOP(4, ""),
    FEATURE_SERVO_TILT(5, ""),
    FEATURE_SOFTSERIAL(6, ""),
    FEATURE_GPS(7, ""),
    FEATURE_SONAR(9, ""),
    FEATURE_TELEMETRY(10, ""),
    FEATURE_3D(12, ""),
    FEATURE_RX_PARALLEL_PWM(13, ""),
    FEATURE_RX_MSP(14, ""),
    FEATURE_RSSI_ADC(15, ""),
    FEATURE_LED_STRIP(16, ""),
    FEATURE_DISPLAY(17, ""),
    FEATURE_BLACKBOX(19, ""),
    FEATURE_CHANNEL_FORWARDING(20, ""),
    FEATURE_FAILSAFE(8, ""),
    FEATURE_TRANSPONDER(21, ""),
    FEATURE_AIRMODE(22, ""),
    FEATURE_OSD(18, ""),
    FEATURE_VTX(24, ""),
    FEATURE_RX_SPI(25, ""),
    FEATURE_ESC_SENSOR(27, ""),
    FEATURE_ANTI_GRAVITY(28, ""),
    FEATURE_DYNAMIC_FILTER(29, ""),
    FEATURE_VBAT(1, ""),
    FEATURE_CURRENT_METER(11, "");

    private final Integer code;
    private final String label;

    MspFeatureEnum(final Integer code, final String label) {
        this.code = code;
        this.label = label;
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
}
