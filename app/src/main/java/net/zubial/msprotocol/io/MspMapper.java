package net.zubial.msprotocol.io;

import android.util.Log;

import net.zubial.msprotocol.data.MspBatteryCurrentData;
import net.zubial.msprotocol.data.MspBatteryVoltageData;
import net.zubial.msprotocol.data.MspData;
import net.zubial.msprotocol.data.MspFeatureData;
import net.zubial.msprotocol.data.MspLiveRcData;
import net.zubial.msprotocol.data.MspModeData;
import net.zubial.msprotocol.enums.MspFeatureEnum;
import net.zubial.msprotocol.enums.MspFlightControllerEnum;
import net.zubial.msprotocol.enums.MspMessageEventEnum;
import net.zubial.msprotocol.enums.MspMessageTypeEnum;
import net.zubial.msprotocol.enums.MspRcChannelEnum;
import net.zubial.msprotocol.enums.MspSdcardStateEnum;
import net.zubial.msprotocol.exceptions.MspBaseException;
import net.zubial.msprotocol.helpers.MspProtocolUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public final class MspMapper {

    private static final String TAG = "MspMapper";

    // In Message
    private static final int MSP_API_VERSION = 1;
    private static final int MSP_FC_VARIANT = 2;
    private static final int MSP_FC_VERSION = 3;
    private static final int MSP_BOARD_INFO = 4;
    private static final int MSP_BUILD_INFO = 5;
    private static final int MSP_NAME = 10;
    private static final int MSP_STATUS_EX = 150;
    private static final int MSP_SDCARD_SUMMARY = 79;
    private static final int MSP_DISPLAYPORT = 182;
    private static final int MSP_DATAFLASH_SUMMARY = 70;
    private static final int MSP_DATAFLASH_READ = 71;
    private static final int MSP_BATTERY_CONFIG = 32;
    private static final int MSP_CURRENT_METER_CONFIG = 40;
    private static final int MSP_VOLTAGE_METER_CONFIG = 56;
    private static final int MSP_MODE_RANGES = 34;
    private static final int MSP_FEATURE_CONFIG = 36;
    private static final int MSP_BOARD_ALIGNMENT_CONFIG = 38;
    private static final int MSP_MIXER_CONFIG = 42;
    private static final int MSP_RX_CONFIG = 44;
    private static final int MSP_RSSI_CONFIG = 50;
    private static final int MSP_ADJUSTMENT_RANGES = 52;
    private static final int MSP_RX_MAP = 64;
    private static final int MSP_TX_INFO = 187;
    private static final int MSP_BOXNAMES = 116;
    private static final int MSP_BOXIDS = 119;
    private static final int MSP_RC_DEADBAND = 125;
    private static final int MSP_LED_COLORS = 46;
    private static final int MSP_LED_STRIP_CONFIG = 48;
    private static final int MSP_LED_STRIP_MODECOLOR = 127;
    private static final int MSP_PID_CONTROLLER = 59;
    private static final int MSP_FILTER_CONFIG = 92;
    private static final int MSP_PID_ADVANCED = 94;
    private static final int MSP_PID = 112;
    private static final int MSP_PIDNAMES = 117;
    private static final int MSP_NAV_CONFIG = 122;
    private static final int MSP_ARMING_CONFIG = 61;
    private static final int MSP_FAILSAFE_CONFIG = 75;
    private static final int MSP_RXFAIL_CONFIG = 77;
    private static final int MSP_BLACKBOX_CONFIG = 80;
    private static final int MSP_TRANSPONDER_CONFIG = 82;
    private static final int MSP_ADVANCED_CONFIG = 90;
    private static final int MSP_BEEPER_CONFIG = 184;
    private static final int MSP_OSD_CONFIG = 84;
    private static final int MSP_VTX_CONFIG = 88;
    private static final int MSP_CAMERA_CONTROL = 98;
    private static final int MSP_OSD_VIDEO_CONFIG = 180;
    private static final int MSP_SENSOR_CONFIG = 96;
    private static final int MSP_GPS_CONFIG = 132;
    private static final int MSP_COMPASS_CONFIG = 133;
    private static final int MSP_SENSOR_ALIGNMENT = 126;
    private static final int MSP_SERVO_CONFIGURATIONS = 120;
    private static final int MSP_MOTOR_3D_CONFIG = 124;
    private static final int MSP_MOTOR_CONFIG = 131;
    private static final int MSP_OSD_CHAR_READ = 86;
    private static final int MSP_SERVO = 103;
    private static final int MSP_MOTOR = 104;
    private static final int MSP_ESC_SENSOR_DATA = 134;
    private static final int MSP_RC = 105;
    private static final int MSP_RC_TUNING = 111;
    private static final int MSP_RAW_GPS = 106;
    private static final int MSP_COMP_GPS = 107;
    private static final int MSP_ATTITUDE = 108;
    private static final int MSP_ALTITUDE = 109;
    private static final int MSP_NAV_STATUS = 121;
    private static final int MSP_WP = 118;
    private static final int MSP_SONAR_ALTITUDE = 58;
    private static final int MSP_RAW_IMU = 102;
    private static final int MSP_ANALOG = 110;
    private static final int MSP_VOLTAGE_METERS = 128;
    private static final int MSP_CURRENT_METERS = 129;
    private static final int MSP_BATTERY_STATE = 130;

    // Out Message
    private static final int MSP_SET_NAME = 11;
    private static final int MSP_EEPROM_WRITE = 250;
    private static final int MSP_REBOOT = 68;
    private static final int MSP_RESET_CONF = 208;
    private static final int MSP_ACC_CALIBRATION = 205;
    private static final int MSP_MAG_CALIBRATION = 206;
    private static final int MSP_COPY_PROFILE = 183;
    private static final int MSP_DATAFLASH_ERASE = 72;
    private static final int MSP_SET_BATTERY_CONFIG = 33;
    private static final int MSP_SET_CURRENT_METER_CONFIG = 41;
    private static final int MSP_SET_VOLTAGE_METER_CONFIG = 57;
    private static final int MSP_SET_MODE_RANGE = 35;
    private static final int MSP_SET_FEATURE_CONFIG = 37;
    private static final int MSP_SET_BOARD_ALIGNMENT_CONFIG = 39;
    private static final int MSP_SET_MIXER_CONFIG = 43;
    private static final int MSP_SET_RX_CONFIG = 45;
    private static final int MSP_SET_RSSI_CONFIG = 51;
    private static final int MSP_SET_ADJUSTMENT_RANGE = 53;
    private static final int MSP_SET_RX_MAP = 65;
    private static final int MSP_SET_TX_INFO = 186;
    private static final int MSP_SET_RC_DEADBAND = 218;
    private static final int MSP_SET_LED_COLORS = 47;
    private static final int MSP_SET_LED_STRIP_CONFIG = 49;
    private static final int MSP_SET_LED_STRIP_MODECOLOR = 221;
    private static final int MSP_SET_PID_CONTROLLER = 60;
    private static final int MSP_SET_FILTER_CONFIG = 93;
    private static final int MSP_SET_PID_ADVANCED = 95;
    private static final int MSP_SET_PID = 202;
    private static final int MSP_SET_NAV_CONFIG = 215;
    private static final int MSP_SELECT_SETTING = 210;
    private static final int MSP_SET_RC_TUNING = 204;
    private static final int MSP_SET_RESET_CURR_PID = 219;
    private static final int MSP_SET_ARMING_CONFIG = 62;
    private static final int MSP_SET_ARMING_DISABLED = 99;
    private static final int MSP_SET_FAILSAFE_CONFIG = 76;
    private static final int MSP_SET_RXFAIL_CONFIG = 78;
    private static final int MSP_SET_BLACKBOX_CONFIG = 81;
    private static final int MSP_SET_TRANSPONDER_CONFIG = 83;
    private static final int MSP_SET_ADVANCED_CONFIG = 91;
    private static final int MSP_SET_BEEPER_CONFIG = 185;
    private static final int MSP_SET_OSD_CONFIG = 85;
    private static final int MSP_SET_VTX_CONFIG = 89;
    private static final int MSP_SET_OSD_VIDEO_CONFIG = 181;
    private static final int MSP_SET_SENSOR_CONFIG = 97;
    private static final int MSP_SET_GPS_CONFIG = 223;
    private static final int MSP_SET_COMPASS_CONFIG = 224;
    private static final int MSP_SET_SENSOR_ALIGNMENT = 220;
    private static final int MSP_SET_MOTOR = 214;
    private static final int MSP_SET_MOTOR_3D_CONFIG = 217;
    private static final int MSP_SET_MOTOR_CONFIG = 222;
    private static final int MSP_OSD_CHAR_WRITE = 87;
    private static final int MSP_SET_SERVO_CONFIGURATION = 212;
    private static final int MSP_SET_RAW_RC = 200;
    private static final int MSP_SET_RAW_GPS = 201;
    private static final int MSP_SET_WP = 209;
    private static final int MSP_SET_HEADING = 211;

    private MspMapper() {

    }

    public static List<MspMessageEventEnum> parseDataMessage(MspData data, MspMessage message) throws MspBaseException {

        List<MspMessageEventEnum> messageEvents = new ArrayList<>();
        MspMessageTypeEnum messageType = message.getMessageType();

        switch (messageType.getCode()) {
            case MSP_API_VERSION:
                data.getMspSystemData().setMspProtocolVersion(message.readUInt8());
                String apiVersion = message.readUInt8() + "." + message.readUInt8();
                data.getMspSystemData().setBoardApiVersion(Double.parseDouble(apiVersion));

                messageEvents.add(MspMessageEventEnum.EVENT_MSP_SYSTEM_DATA);

                break;

            case MSP_FC_VARIANT:
                data.getMspSystemData().setBoardFlightControllerIdentifier(MspFlightControllerEnum.findByCode(message.readString()));

                messageEvents.add(MspMessageEventEnum.EVENT_MSP_SYSTEM_DATA);

                break;

            case MSP_FC_VERSION:
                data.getMspSystemData().setBoardFlightControllerVersion(message.readUInt8() + "." + message.readUInt8() + "." + message.readUInt8());

                messageEvents.add(MspMessageEventEnum.EVENT_MSP_SYSTEM_DATA);

                break;

            case MSP_BOARD_INFO:
                data.getMspSystemData().setBoardIdentifier(message.readString(4));
                data.getMspSystemData().setBoardVersion(message.readUInt16());

                if (MspProtocolUtils.gtVersion(data, 1.35)) {
                    data.getMspSystemData().setBoardType(message.readUInt8());
                } else {
                    data.getMspSystemData().setBoardType(0);
                }

                messageEvents.add(MspMessageEventEnum.EVENT_MSP_SYSTEM_DATA);

                break;

            case MSP_BUILD_INFO:
                data.getMspSystemData().setBoardBuildInfo(message.readString());

                messageEvents.add(MspMessageEventEnum.EVENT_MSP_SYSTEM_DATA);

                break;

            case MSP_NAME:
                data.getMspSystemData().setBoardName(message.readString());

                messageEvents.add(MspMessageEventEnum.EVENT_MSP_SYSTEM_DATA);

                break;

            case MSP_STATUS_EX:
                data.getMspLiveData().setStatusCycleTime(message.readUInt16());
                data.getMspLiveData().setStatusI2cError(message.readUInt16());
                data.getMspSystemData().setStatusActiveSensors(message.readUInt16());
                data.getMspSystemData().setStatusMode(message.readUInt32());
                data.getMspSystemData().setStatusProfile(message.readUInt8());
                data.getMspLiveData().setStatusCpuload(message.readUInt16());
                if (MspProtocolUtils.gtVersion(data, 1.16)) {
                    data.getMspSystemData().setStatusNumProfiles(message.readUInt8());
                    data.getMspSystemData().setStatusRateProfile(message.readUInt8());

                    // TODO armingDisableFlags
                    //if (gtVersion(data, 1.36)) {

                    //}
                }

                // Check sensor
                Log.d(TAG, "StatusActiveSensors - " + data.getMspSystemData().getStatusActiveSensors());
                data.getMspSystemData().setStatusHaveAccel(MspProtocolUtils.bitCheck(data.getMspSystemData().getStatusActiveSensors(), 0));
                data.getMspSystemData().setStatusHaveBaro(MspProtocolUtils.bitCheck(data.getMspSystemData().getStatusActiveSensors(), 1));
                data.getMspSystemData().setStatusHaveMag(MspProtocolUtils.bitCheck(data.getMspSystemData().getStatusActiveSensors(), 2));
                data.getMspSystemData().setStatusHaveGps(MspProtocolUtils.bitCheck(data.getMspSystemData().getStatusActiveSensors(), 3));
                data.getMspSystemData().setStatusHaveSonar(MspProtocolUtils.bitCheck(data.getMspSystemData().getStatusActiveSensors(), 4));
                if (MspProtocolUtils.gtVersion(data, 1.36)) {
                    data.getMspSystemData().setStatusHaveGyro(MspProtocolUtils.bitCheck(data.getMspSystemData().getStatusActiveSensors(), 5));
                } else {
                    data.getMspSystemData().setStatusHaveGyro(true);
                }

                messageEvents.add(MspMessageEventEnum.EVENT_MSP_SYSTEM_DATA);
                messageEvents.add(MspMessageEventEnum.EVENT_MSP_LIVE_DATA);

                break;

            case MSP_SDCARD_SUMMARY:
                data.getMspSystemData().setSdcardSupported((message.readInt8() & 0x01) != 0);
                data.getMspSystemData().setSdcardState(MspSdcardStateEnum.findByCode(message.readUInt8()));
                data.getMspSystemData().setSdcardFsError(message.readUInt8());
                data.getMspSystemData().setSdcardFreeSize(message.readUInt32());
                data.getMspSystemData().setSdcardTotalSize(message.readUInt32());

                messageEvents.add(MspMessageEventEnum.EVENT_MSP_SYSTEM_DATA);

                break;

            case MSP_BATTERY_CONFIG:
                data.getMspBatteryData().setVbatMinCellVoltage(message.readUInt8());
                data.getMspBatteryData().setVbatMaxCellVoltage(message.readUInt8());
                data.getMspBatteryData().setVbatWarningCellVoltage(message.readUInt8());
                data.getMspBatteryData().setCapacity(message.readUInt16());
                data.getMspBatteryData().setVoltageMeterSource(message.readUInt8());
                data.getMspBatteryData().setCurrentMeterSource(message.readUInt8());

                messageEvents.add(MspMessageEventEnum.EVENT_MSP_BATTERY_DATA);

                break;

            case MSP_VOLTAGE_METER_CONFIG:
                data.getMspBatteryData().getVoltageData().clear();

                if (MspProtocolUtils.ltVersion(data, 1.36)) {
                    MspBatteryVoltageData voltageData = new MspBatteryVoltageData();
                    voltageData.setId(0);
                    voltageData.setScale(message.readUInt8());
                    data.getMspBatteryData().setVbatMinCellVoltage(message.readUInt8());
                    data.getMspBatteryData().setVbatMaxCellVoltage(message.readUInt8());
                    data.getMspBatteryData().setVbatWarningCellVoltage(message.readUInt8());

                    if (MspProtocolUtils.gtVersion(data, 1.23)) {
                        data.getMspBatteryData().setVoltageMeterSource(message.readUInt8());
                    }

                    data.getMspBatteryData().getVoltageData().add(voltageData);
                } else {
                    int voltage_meter_count = message.readUInt8();

                    for (int i = 0; i < voltage_meter_count; i++) {
                        int subframe_length = message.readUInt8();
                        if (subframe_length == 5) {
                            MspBatteryVoltageData voltageData = new MspBatteryVoltageData();
                            voltageData.setId(message.readUInt8());
                            voltageData.setSensorType(message.readUInt8());
                            voltageData.setScale(message.readUInt8());
                            voltageData.setVbatResdivVal(message.readUInt8());
                            voltageData.setVbatResdivMultiplier(message.readUInt8());

                            data.getMspBatteryData().getVoltageData().add(voltageData);
                        }
                    }
                }

                messageEvents.add(MspMessageEventEnum.EVENT_MSP_BATTERY_DATA);

                break;

            case MSP_CURRENT_METER_CONFIG:
                data.getMspBatteryData().getCurrentData().clear();

                if (MspProtocolUtils.ltVersion(data, 1.36)) {
                    MspBatteryCurrentData currentData = new MspBatteryCurrentData();
                    currentData.setScale(message.readUInt16());
                    currentData.setOffset(message.readUInt16());
                    data.getMspBatteryData().setCurrentMeterSource(message.readUInt8());
                    data.getMspBatteryData().setCapacity(message.readUInt16());

                } else {
                    int current_meter_count = message.readUInt8();

                    for (int i = 0; i < current_meter_count; i++) {
                        int subframe_length = message.readUInt8();
                        if (subframe_length == 6) {
                            MspBatteryCurrentData currentData = new MspBatteryCurrentData();
                            currentData.setId(message.readUInt8());
                            currentData.setSensorType(message.readUInt8());
                            currentData.setScale(message.readUInt16());
                            currentData.setOffset(message.readUInt16());

                            data.getMspBatteryData().getCurrentData().add(currentData);
                        }
                    }
                }

                messageEvents.add(MspMessageEventEnum.EVENT_MSP_BATTERY_DATA);

                break;

            case MSP_FEATURE_CONFIG:
                int featureConfig = message.readUInt32();
                Log.d(TAG, "Feature - " + featureConfig);
                Log.d(TAG, "Version - " + data.getMspSystemData().getBoardApiVersion());

                data.setMspFeaturesMask(featureConfig);

                data.getMspFeatures().clear();
                data.getMspFeatures().add(new MspFeatureData(MspFeatureEnum.FEATURE_RX_PPM, MspProtocolUtils.bitCheck(featureConfig, MspFeatureEnum.FEATURE_RX_PPM.getCode())));
                data.getMspFeatures().add(new MspFeatureData(MspFeatureEnum.FEATURE_INFLIGHT_ACC_CAL, MspProtocolUtils.bitCheck(featureConfig, MspFeatureEnum.FEATURE_INFLIGHT_ACC_CAL.getCode())));
                data.getMspFeatures().add(new MspFeatureData(MspFeatureEnum.FEATURE_RX_SERIAL, MspProtocolUtils.bitCheck(featureConfig, MspFeatureEnum.FEATURE_RX_SERIAL.getCode())));
                data.getMspFeatures().add(new MspFeatureData(MspFeatureEnum.FEATURE_MOTOR_STOP, MspProtocolUtils.bitCheck(featureConfig, MspFeatureEnum.FEATURE_MOTOR_STOP.getCode())));
                data.getMspFeatures().add(new MspFeatureData(MspFeatureEnum.FEATURE_SERVO_TILT, MspProtocolUtils.bitCheck(featureConfig, MspFeatureEnum.FEATURE_SERVO_TILT.getCode())));
                data.getMspFeatures().add(new MspFeatureData(MspFeatureEnum.FEATURE_SOFTSERIAL, MspProtocolUtils.bitCheck(featureConfig, MspFeatureEnum.FEATURE_SOFTSERIAL.getCode())));
                data.getMspFeatures().add(new MspFeatureData(MspFeatureEnum.FEATURE_GPS, MspProtocolUtils.bitCheck(featureConfig, MspFeatureEnum.FEATURE_GPS.getCode())));
                data.getMspFeatures().add(new MspFeatureData(MspFeatureEnum.FEATURE_SONAR, MspProtocolUtils.bitCheck(featureConfig, MspFeatureEnum.FEATURE_SONAR.getCode())));
                data.getMspFeatures().add(new MspFeatureData(MspFeatureEnum.FEATURE_TELEMETRY, MspProtocolUtils.bitCheck(featureConfig, MspFeatureEnum.FEATURE_TELEMETRY.getCode())));
                data.getMspFeatures().add(new MspFeatureData(MspFeatureEnum.FEATURE_3D, MspProtocolUtils.bitCheck(featureConfig, MspFeatureEnum.FEATURE_3D.getCode())));
                data.getMspFeatures().add(new MspFeatureData(MspFeatureEnum.FEATURE_RX_PARALLEL_PWM, MspProtocolUtils.bitCheck(featureConfig, MspFeatureEnum.FEATURE_RX_PARALLEL_PWM.getCode())));
                data.getMspFeatures().add(new MspFeatureData(MspFeatureEnum.FEATURE_RX_MSP, MspProtocolUtils.bitCheck(featureConfig, MspFeatureEnum.FEATURE_RX_MSP.getCode())));
                data.getMspFeatures().add(new MspFeatureData(MspFeatureEnum.FEATURE_RSSI_ADC, MspProtocolUtils.bitCheck(featureConfig, MspFeatureEnum.FEATURE_RSSI_ADC.getCode())));
                data.getMspFeatures().add(new MspFeatureData(MspFeatureEnum.FEATURE_LED_STRIP, MspProtocolUtils.bitCheck(featureConfig, MspFeatureEnum.FEATURE_LED_STRIP.getCode())));
                data.getMspFeatures().add(new MspFeatureData(MspFeatureEnum.FEATURE_DISPLAY, MspProtocolUtils.bitCheck(featureConfig, MspFeatureEnum.FEATURE_DISPLAY.getCode())));

                if (!MspProtocolUtils.gtVersion(data, 1.33)) {
                    data.getMspFeatures().add(new MspFeatureData(MspFeatureEnum.FEATURE_BLACKBOX, MspProtocolUtils.bitCheck(featureConfig, MspFeatureEnum.FEATURE_BLACKBOX.getCode())));
                }

                if (MspProtocolUtils.gtVersion(data, 1.12)) {
                    data.getMspFeatures().add(new MspFeatureData(MspFeatureEnum.FEATURE_CHANNEL_FORWARDING, MspProtocolUtils.bitCheck(featureConfig, MspFeatureEnum.FEATURE_CHANNEL_FORWARDING.getCode())));
                }

                if (MspProtocolUtils.gtVersion(data, 1.15) && !MspProtocolUtils.gtVersion(data, 1.36)) {
                    data.getMspFeatures().add(new MspFeatureData(MspFeatureEnum.FEATURE_FAILSAFE, MspProtocolUtils.bitCheck(featureConfig, MspFeatureEnum.FEATURE_FAILSAFE.getCode())));
                }

                if (MspProtocolUtils.gtVersion(data, 1.16)) {
                    data.getMspFeatures().add(new MspFeatureData(MspFeatureEnum.FEATURE_TRANSPONDER, MspProtocolUtils.bitCheck(featureConfig, MspFeatureEnum.FEATURE_TRANSPONDER.getCode())));
                }

                if (data.getMspSystemData().getBoardFlightControllerVersion() != null
                        && !data.getMspSystemData().getBoardFlightControllerVersion().isEmpty()) {
                    if (MspProtocolUtils.gtVersion(data, 1.16)) {
                        data.getMspFeatures().add(new MspFeatureData(MspFeatureEnum.FEATURE_AIRMODE, MspProtocolUtils.bitCheck(featureConfig, MspFeatureEnum.FEATURE_AIRMODE.getCode())));

                        if (MspProtocolUtils.ltVersion(data, 1.20)) {
                            data.getMspFeatures().add(new MspFeatureData(MspFeatureEnum.FEATURE_SUPEREXPO_RATES, MspProtocolUtils.bitCheck(featureConfig, MspFeatureEnum.FEATURE_SUPEREXPO_RATES.getCode())));
                        } else if (!MspProtocolUtils.gtVersion(data, 1.33)) {
                            data.getMspFeatures().add(new MspFeatureData(MspFeatureEnum.FEATURE_SDCARD, MspProtocolUtils.bitCheck(featureConfig, MspFeatureEnum.FEATURE_SDCARD.getCode())));
                        }
                    }

                    if (MspProtocolUtils.gtVersion(data, 1.20)) {
                        data.getMspFeatures().add(new MspFeatureData(MspFeatureEnum.FEATURE_OSD, MspProtocolUtils.bitCheck(featureConfig, MspFeatureEnum.FEATURE_OSD.getCode())));

                        if (!MspProtocolUtils.gtVersion(data, 1.35)) {
                            data.getMspFeatures().add(new MspFeatureData(MspFeatureEnum.FEATURE_VTX, MspProtocolUtils.bitCheck(featureConfig, MspFeatureEnum.FEATURE_VTX.getCode())));
                        }
                    }

                    if (MspProtocolUtils.gtVersion(data, 1.31)) {
                        data.getMspFeatures().add(new MspFeatureData(MspFeatureEnum.FEATURE_RX_SPI, MspProtocolUtils.bitCheck(featureConfig, MspFeatureEnum.FEATURE_RX_SPI.getCode())));
                        data.getMspFeatures().add(new MspFeatureData(MspFeatureEnum.FEATURE_ESC_SENSOR, MspProtocolUtils.bitCheck(featureConfig, MspFeatureEnum.FEATURE_ESC_SENSOR.getCode())));
                    }

                    if (MspProtocolUtils.gtVersion(data, 1.36)) {
                        data.getMspFeatures().add(new MspFeatureData(MspFeatureEnum.FEATURE_ANTI_GRAVITY, MspProtocolUtils.bitCheck(featureConfig, MspFeatureEnum.FEATURE_ANTI_GRAVITY.getCode())));
                        data.getMspFeatures().add(new MspFeatureData(MspFeatureEnum.FEATURE_DYNAMIC_FILTER, MspProtocolUtils.bitCheck(featureConfig, MspFeatureEnum.FEATURE_DYNAMIC_FILTER.getCode())));
                    }

                    if (!MspProtocolUtils.gtVersion(data, 1.36)) {
                        data.getMspFeatures().add(new MspFeatureData(MspFeatureEnum.FEATURE_VBAT, MspProtocolUtils.bitCheck(featureConfig, MspFeatureEnum.FEATURE_VBAT.getCode())));
                        data.getMspFeatures().add(new MspFeatureData(MspFeatureEnum.FEATURE_CURRENT_METER, MspProtocolUtils.bitCheck(featureConfig, MspFeatureEnum.FEATURE_CURRENT_METER.getCode())));
                    }
                }

                messageEvents.add(MspMessageEventEnum.EVENT_MSP_FEATURE_DATA);

                break;

            case MSP_MODE_RANGES:
                data.getMspModesData().getMspModes().clear();

                Integer modeRangeCount = message.getSize() / 4;
                for (int i = 0; i < modeRangeCount; i++) {
                    MspModeData modeData = new MspModeData();
                    modeData.setId(i);
                    modeData.setIndex(message.readUInt8());
                    modeData.setAuxChannel(message.readUInt8());
                    modeData.setRangeStart(900 + (message.readUInt8() * 25));
                    modeData.setRangeEnd(900 + (message.readUInt8() * 25));
                    modeData.setEnable(!modeData.getRangeEnd().equals(900));

                    if (!data.getMspModesData().getMspModeIds().isEmpty()
                            && !data.getMspModesData().getMspModeNames().isEmpty()
                            && modeData.getEnable()) {

                        for (Map.Entry<Integer, Integer> entry : data.getMspModesData().getMspModeIds().entrySet()) {
                            if (entry.getValue().equals(modeData.getIndex())) {
                                modeData.setModeName(data.getMspModesData().getMspModeNames().get(entry.getKey()));
                                break;
                            }
                        }

                        data.getMspModesData().getMspModes().add(modeData);
                    }
                }

                messageEvents.add(MspMessageEventEnum.EVENT_MSP_MODES_DATA);

                break;

            case MSP_BOXIDS:
                data.getMspModesData().getMspModeIds().clear();

                for (int i = 0; i < message.getSize(); i++) {
                    data.getMspModesData().getMspModeIds().put(i, message.readUInt8());
                }

                break;

            case MSP_BOXNAMES:
                data.getMspModesData().getMspModeNames().clear();

                String modes = message.readString();
                String[] modeSplit = modes.split(";");

                for (int i = 0; i < modeSplit.length; i++) {
                    data.getMspModesData().getMspModeNames().put(i, modeSplit[i]);
                }

                messageEvents.add(MspMessageEventEnum.EVENT_MSP_MODES_DATA);

                break;

            case MSP_ANALOG:
                data.getMspLiveData().setVoltage(message.readUInt8() / 10.0);
                data.getMspLiveData().setmAhDrawn(message.readUInt16());
                data.getMspLiveData().setRssi(message.readUInt16());
                data.getMspLiveData().setAmperage(message.readInt16() / 100.0);

                messageEvents.add(MspMessageEventEnum.EVENT_MSP_LIVE_DATA);

                break;

            case MSP_RAW_IMU:
                data.getMspLiveData().setAccelerometerX(message.readInt16() / 512.0);
                data.getMspLiveData().setAccelerometerY(message.readInt16() / 512.0);
                data.getMspLiveData().setAccelerometerZ(message.readInt16() / 512.0);

                data.getMspLiveData().setGyroscopeX(message.readInt16() * (4 / 16.4));
                data.getMspLiveData().setGyroscopeY(message.readInt16() * (4 / 16.4));
                data.getMspLiveData().setGyroscopeZ(message.readInt16() * (4 / 16.4));

                data.getMspLiveData().setMagnetometerX(message.readInt16() / 1090.0);
                data.getMspLiveData().setMagnetometerY(message.readInt16() / 1090.0);
                data.getMspLiveData().setMagnetometerZ(message.readInt16() / 1090.0);

                messageEvents.add(MspMessageEventEnum.EVENT_MSP_LIVE_DATA);

                break;

            case MSP_ATTITUDE:
                data.getMspLiveData().setKinematicsX(message.readInt16() / 10.0);
                data.getMspLiveData().setKinematicsY(message.readInt16() / 10.0);
                data.getMspLiveData().setKinematicsZ(message.readInt16() / 1.0);

                messageEvents.add(MspMessageEventEnum.EVENT_MSP_LIVE_DATA);

                break;

            case MSP_ALTITUDE:
                data.getMspLiveData().setAltitude(message.readInt32() / 100.0);

                messageEvents.add(MspMessageEventEnum.EVENT_MSP_LIVE_DATA);

                break;

            case MSP_RC:
                data.getMspLiveData().getMspLiveRc().clear();

                int active_channels = message.getSize() / 2;
                for (int i = 0; i < active_channels; i++) {
                    MspLiveRcData rcData = new MspLiveRcData();
                    rcData.setId(i);
                    rcData.setChannel(MspRcChannelEnum.findByCode(i));
                    rcData.setValue(message.readUInt16());
                    data.getMspLiveData().getMspLiveRc().add(rcData);
                }

                messageEvents.add(MspMessageEventEnum.EVENT_MSP_LIVE_DATA);

                break;

            default:
                return parseConfirmMessage(data, message);
        }

        return messageEvents;
    }

    private static List<MspMessageEventEnum> parseConfirmMessage(MspData data, MspMessage message) throws MspBaseException {

        List<MspMessageEventEnum> messageEvents = new ArrayList<>();
        MspMessageTypeEnum messageType = message.getMessageType();

        switch (messageType.getCode()) {
            case MSP_ACC_CALIBRATION:
            case MSP_COPY_PROFILE:
            case MSP_DATAFLASH_ERASE:
            case MSP_EEPROM_WRITE:
            case MSP_MAG_CALIBRATION:
            case MSP_OSD_CHAR_WRITE:
            case MSP_REBOOT:
            case MSP_RESET_CONF:
            case MSP_SELECT_SETTING:

                messageEvents.add(MspMessageEventEnum.EVENT_MSP_DONE);

                break;

            case MSP_SET_ADJUSTMENT_RANGE:
            case MSP_SET_ADVANCED_CONFIG:
            case MSP_SET_ARMING_CONFIG:
            case MSP_SET_ARMING_DISABLED:
            case MSP_SET_BATTERY_CONFIG:
            case MSP_SET_BEEPER_CONFIG:
            case MSP_SET_BLACKBOX_CONFIG:
            case MSP_SET_BOARD_ALIGNMENT_CONFIG:
            case MSP_SET_COMPASS_CONFIG:
            case MSP_SET_CURRENT_METER_CONFIG:
            case MSP_SET_FAILSAFE_CONFIG:
            case MSP_SET_FEATURE_CONFIG:
            case MSP_SET_FILTER_CONFIG:
            case MSP_SET_GPS_CONFIG:
            case MSP_SET_HEADING:
            case MSP_SET_LED_COLORS:
            case MSP_SET_LED_STRIP_CONFIG:
            case MSP_SET_LED_STRIP_MODECOLOR:
            case MSP_SET_MIXER_CONFIG:
            case MSP_SET_MODE_RANGE:
            case MSP_SET_MOTOR:
            case MSP_SET_MOTOR_3D_CONFIG:
            case MSP_SET_MOTOR_CONFIG:
            case MSP_SET_NAME:
            case MSP_SET_NAV_CONFIG:
            case MSP_SET_OSD_CONFIG:
            case MSP_SET_OSD_VIDEO_CONFIG:
            case MSP_SET_PID:
            case MSP_SET_PID_ADVANCED:
            case MSP_SET_PID_CONTROLLER:
            case MSP_SET_RAW_GPS:
            case MSP_SET_RAW_RC:
            case MSP_SET_RC_DEADBAND:
            case MSP_SET_RC_TUNING:
            case MSP_SET_RESET_CURR_PID:
            case MSP_SET_RSSI_CONFIG:
            case MSP_SET_RX_CONFIG:
            case MSP_SET_RX_MAP:
            case MSP_SET_RXFAIL_CONFIG:
            case MSP_SET_SENSOR_ALIGNMENT:
            case MSP_SET_SENSOR_CONFIG:
            case MSP_SET_SERVO_CONFIGURATION:
            case MSP_SET_TRANSPONDER_CONFIG:
            case MSP_SET_TX_INFO:
            case MSP_SET_VOLTAGE_METER_CONFIG:
            case MSP_SET_VTX_CONFIG:
            case MSP_SET_WP:

                messageEvents.add(MspMessageEventEnum.EVENT_MSP_UPDATED);

                break;

            default:
                Log.d(TAG, "Parser for " + messageType.getCode() + " not found");

        }

        return messageEvents;
    }
}
