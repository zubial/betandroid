package com.betandroid.msprotocol.enums;

public enum MspMessageTypeEnum {

    MSP_API_VERSION(1,(byte)1),
    MSP_FC_VARIANT(2,(byte)2),
    MSP_FC_VERSION(3,(byte)3),
    MSP_BOARD_INFO(4,(byte)4),
    MSP_BUILD_INFO(5,(byte)5),
    MSP_NAME(10,(byte)10),
    MSP_SET_NAME(11,(byte)11),
    MSP_EEPROM_WRITE(250,(byte)250),
    MSP_REBOOT(68,(byte)68),
    MSP_RESET_CONF(208,(byte)208),
    MSP_ACC_CALIBRATION(205,(byte)205),
    MSP_MAG_CALIBRATION(206,(byte)206),
    MSP_STATUS_EX(150,(byte)150),
    MSP_SDCARD_SUMMARY(79,(byte)79),
    MSP_DISPLAYPORT(182,(byte)182),
    MSP_COPY_PROFILE(183,(byte)183),
    MSP_DATAFLASH_SUMMARY(70,(byte)70),
    MSP_DATAFLASH_READ(71,(byte)71),
    MSP_DATAFLASH_ERASE(72,(byte)72),
    MSP_BATTERY_CONFIG(32,(byte)32),
    MSP_SET_BATTERY_CONFIG(33,(byte)33),
    MSP_CURRENT_METER_CONFIG(40,(byte)40),
    MSP_SET_CURRENT_METER_CONFIG(41,(byte)41),
    MSP_VOLTAGE_METER_CONFIG(56,(byte)56),
    MSP_SET_VOLTAGE_METER_CONFIG(57,(byte)57),
    MSP_MODE_RANGES(34,(byte)34),
    MSP_SET_MODE_RANGE(35,(byte)35),
    MSP_FEATURE_CONFIG(36,(byte)36),
    MSP_SET_FEATURE_CONFIG(37,(byte)37),
    MSP_BOARD_ALIGNMENT_CONFIG(38,(byte)38),
    MSP_SET_BOARD_ALIGNMENT_CONFIG(39,(byte)39),
    MSP_MIXER_CONFIG(42,(byte)42),
    MSP_SET_MIXER_CONFIG(43,(byte)43),
    MSP_RX_CONFIG(44,(byte)44),
    MSP_SET_RX_CONFIG(45,(byte)45),
    MSP_RSSI_CONFIG(50,(byte)50),
    MSP_SET_RSSI_CONFIG(51,(byte)51),
    MSP_ADJUSTMENT_RANGES(52,(byte)52),
    MSP_SET_ADJUSTMENT_RANGE(53,(byte)53),
    MSP_RX_MAP(64,(byte)64),
    MSP_SET_RX_MAP(65,(byte)65),
    MSP_SET_TX_INFO(186,(byte)186),
    MSP_TX_INFO(187,(byte)187),
    MSP_BOXNAMES(116,(byte)116),
    MSP_BOXIDS(119,(byte)119),
    MSP_RC_DEADBAND(125,(byte)125),
    MSP_SET_RC_DEADBAND(218,(byte)218),
    MSP_LED_COLORS(46,(byte)46),
    MSP_SET_LED_COLORS(47,(byte)47),
    MSP_LED_STRIP_CONFIG(48,(byte)48),
    MSP_SET_LED_STRIP_CONFIG(49,(byte)49),
    MSP_LED_STRIP_MODECOLOR(127,(byte)127),
    MSP_SET_LED_STRIP_MODECOLOR(221,(byte)221),
    MSP_PID_CONTROLLER(59,(byte)59),
    MSP_SET_PID_CONTROLLER(60,(byte)60),
    MSP_FILTER_CONFIG(92,(byte)92),
    MSP_SET_FILTER_CONFIG(93,(byte)93),
    MSP_PID_ADVANCED(94,(byte)94),
    MSP_SET_PID_ADVANCED(95,(byte)95),
    MSP_PID(112,(byte)112),
    MSP_SET_PID(202,(byte)202),
    MSP_PIDNAMES(117,(byte)117),
    MSP_NAV_CONFIG(122,(byte)122),
    MSP_SET_NAV_CONFIG(215,(byte)215),
    MSP_SELECT_SETTING(210,(byte)210),
    MSP_SET_RC_TUNING(204,(byte)204),
    MSP_SET_RESET_CURR_PID(219,(byte)219),
    MSP_ARMING_CONFIG(61,(byte)61),
    MSP_SET_ARMING_CONFIG(62,(byte)62),
    MSP_SET_ARMING_DISABLED(99,(byte)99),
    MSP_FAILSAFE_CONFIG(75,(byte)75),
    MSP_SET_FAILSAFE_CONFIG(76,(byte)76),
    MSP_RXFAIL_CONFIG(77,(byte)77),
    MSP_SET_RXFAIL_CONFIG(78,(byte)78),
    MSP_BLACKBOX_CONFIG(80,(byte)80),
    MSP_SET_BLACKBOX_CONFIG(81,(byte)81),
    MSP_TRANSPONDER_CONFIG(82,(byte)82),
    MSP_SET_TRANSPONDER_CONFIG(83,(byte)83),
    MSP_ADVANCED_CONFIG(90,(byte)90),
    MSP_SET_ADVANCED_CONFIG(91,(byte)91),
    MSP_BEEPER_CONFIG(184,(byte)184),
    MSP_SET_BEEPER_CONFIG(185,(byte)185),
    MSP_OSD_CONFIG(84,(byte)84),
    MSP_SET_OSD_CONFIG(85,(byte)85),
    MSP_VTX_CONFIG(88,(byte)88),
    MSP_SET_VTX_CONFIG(89,(byte)89),
    MSP_CAMERA_CONTROL(98,(byte)98),
    MSP_OSD_VIDEO_CONFIG(180,(byte)180),
    MSP_SET_OSD_VIDEO_CONFIG(181,(byte)181),
    MSP_SENSOR_CONFIG(96,(byte)96),
    MSP_SET_SENSOR_CONFIG(97,(byte)97),
    MSP_GPS_CONFIG(132,(byte)132),
    MSP_SET_GPS_CONFIG(223,(byte)223),
    MSP_COMPASS_CONFIG(133,(byte)133),
    MSP_SET_COMPASS_CONFIG(224,(byte)224),
    MSP_SENSOR_ALIGNMENT(126,(byte)126),
    MSP_SET_SENSOR_ALIGNMENT(220,(byte)220),
    MSP_SERVO_CONFIGURATIONS(120,(byte)120),
    MSP_SET_MOTOR(214,(byte)214),
    MSP_MOTOR_3D_CONFIG(124,(byte)124),
    MSP_SET_MOTOR_3D_CONFIG(217,(byte)217),
    MSP_MOTOR_CONFIG(131,(byte)131),
    MSP_SET_MOTOR_CONFIG(222,(byte)222),
    MSP_OSD_CHAR_READ(86,(byte)86),
    MSP_OSD_CHAR_WRITE(87,(byte)87),
    MSP_SERVO(103,(byte)103),
    MSP_SET_SERVO_CONFIGURATION(212,(byte)212),
    MSP_MOTOR(104,(byte)104),
    MSP_ESC_SENSOR_DATA(134,(byte)134),
    MSP_RC(105,(byte)105),
    MSP_RC_TUNING(111,(byte)111),
    MSP_SET_RAW_RC(200,(byte)200),
    MSP_RAW_GPS(106,(byte)106),
    MSP_COMP_GPS(107,(byte)107),
    MSP_ATTITUDE(108,(byte)108),
    MSP_ALTITUDE(109,(byte)109),
    MSP_NAV_STATUS(121,(byte)121),
    MSP_SET_RAW_GPS(201,(byte)201),
    MSP_WP(118,(byte)118),
    MSP_SET_WP(209,(byte)209),
    MSP_SET_HEADING(211,(byte)211),
    MSP_SONAR_ALTITUDE(58,(byte)58),
    MSP_RAW_IMU(102,(byte)102),
    MSP_ANALOG(110,(byte)110),
    MSP_VOLTAGE_METERS(128,(byte)128),
    MSP_CURRENT_METERS(129,(byte)129),
    MSP_BATTERY_STATE(130,(byte)130),

    MSP_UNKNOW(0,(byte)0);

    private final int code;
    private final byte value;

    MspMessageTypeEnum(final int code, final byte value) {
        this.code = code;
        this.value = value;
    }

    public byte getValue() {
        return value;
    }

    public int getCode() {
        return code;
    }

    public static MspMessageTypeEnum findByValue(final byte s) {
        for (final MspMessageTypeEnum e : MspMessageTypeEnum.values()) {
            if (e.getValue() == s) {
                return e;
            }
        }
        return MSP_UNKNOW;
    }

    public Boolean isEqual(MspMessageTypeEnum compare) {
        return (this.getCode() == compare.getCode());
    }
}
