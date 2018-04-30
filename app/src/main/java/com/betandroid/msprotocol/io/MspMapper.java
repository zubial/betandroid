package com.betandroid.msprotocol.io;

import android.util.Log;

import com.betandroid.msprotocol.data.MspBatteryCurrentData;
import com.betandroid.msprotocol.data.MspBatteryVoltageData;
import com.betandroid.msprotocol.data.MspData;
import com.betandroid.msprotocol.enums.MspFlightControllerEnum;
import com.betandroid.msprotocol.enums.MspMessageTypeEnum;
import com.betandroid.msprotocol.enums.MspSdcardStateEnum;
import com.betandroid.msprotocol.exceptions.MspBaseException;
import com.betandroid.msprotocol.helpers.MspUtils;

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

    private MspMapper() {

    }

    public static Boolean gtVersion(MspData data, Double version) {
        if (data != null && version != null)  {
            return (data.getMspSystemData().getBoardApiVersion().compareTo(version) > 0);
        }
        return false;
    }

    public static Boolean ltVersion(MspData data, Double version) {
        if (data != null && version != null)  {
            return (data.getMspSystemData().getBoardApiVersion().compareTo(version) < 0);
        }
        return false;
    }

    public static Boolean eqVersion(MspData data, Double version) {
        if (data != null && version != null)  {
            return (data.getMspSystemData().getBoardApiVersion().compareTo(version) == 0);
        }
        return false;
    }

    public static void parseMessage(MspData data, MspMessage message) throws MspBaseException {

        MspMessageTypeEnum messageType = message.getMessageType();

        switch (messageType.getCode()) {
            case MSP_API_VERSION:
                data.getMspSystemData().setMspProtocolVersion(message.readUInt8());
                String apiVersion = message.readUInt8() + "." + message.readUInt8();
                data.getMspSystemData().setBoardApiVersion(Double.parseDouble(apiVersion));
                break;

            case MSP_FC_VARIANT:
                data.getMspSystemData().setBoardFlightControllerIdentifier(MspFlightControllerEnum.findByCode(message.readString()));
                break;

            case MSP_FC_VERSION:
                data.getMspSystemData().setBoardFlightControllerVersion(message.readUInt8() + "." + message.readUInt8() + "." + message.readUInt8());
                break;

            case MSP_BOARD_INFO:
                data.getMspSystemData().setBoardIdentifier(message.readString(4));
                data.getMspSystemData().setBoardVersion(message.readUInt16());

                if (gtVersion(data, 1.35)) {
                    data.getMspSystemData().setBoardType(message.readUInt8());
                } else {
                    data.getMspSystemData().setBoardType(0);
                }
                break;

            case MSP_BUILD_INFO:
                data.getMspSystemData().setBoardBuildInfo(message.readString());
                break;

            case MSP_NAME:
                data.getMspSystemData().setBoardName(message.readString());
                break;

            case MSP_STATUS_EX:
                data.getMspSystemData().setStatusCycleTime(message.readUInt16());
                data.getMspSystemData().setStatusI2cError(message.readUInt16());
                data.getMspSystemData().setStatusActiveSensors(message.readUInt16());
                data.getMspSystemData().setStatusMode(message.readUInt32());
                data.getMspSystemData().setStatusProfile(message.readUInt8());
                data.getMspSystemData().setStatusCpuload(message.readUInt16());
                if (gtVersion(data, 1.16)) {
                    data.getMspSystemData().setStatusNumProfiles(message.readUInt8());
                    data.getMspSystemData().setStatusRateProfile(message.readUInt8());

                    if (gtVersion(data, 1.36)) {
                        // armingDisableFlags
                    }
                }

                // Check sensor
                Log.d(TAG, "StatusActiveSensors - " + data.getMspSystemData().getStatusActiveSensors());
                data.getMspSystemData().setStatusHaveAccel(MspUtils.bitCheck(data.getMspSystemData().getStatusActiveSensors(), 0));
                data.getMspSystemData().setStatusHaveGyro(MspUtils.bitCheck(data.getMspSystemData().getStatusActiveSensors(), 1));
                data.getMspSystemData().setStatusHaveBaro(MspUtils.bitCheck(data.getMspSystemData().getStatusActiveSensors(), 2));
                data.getMspSystemData().setStatusHaveMag(MspUtils.bitCheck(data.getMspSystemData().getStatusActiveSensors(), 3));
                data.getMspSystemData().setStatusHaveGps(MspUtils.bitCheck(data.getMspSystemData().getStatusActiveSensors(), 4));
                data.getMspSystemData().setStatusHaveSonar(MspUtils.bitCheck(data.getMspSystemData().getStatusActiveSensors(), 5));

                break;

            case MSP_SDCARD_SUMMARY:
                data.getMspSystemData().setSdcardSupported((message.readInt8() & 0x01) != 0);
                data.getMspSystemData().setSdcardState(MspSdcardStateEnum.findByCode(message.readUInt8()));
                data.getMspSystemData().setSdcardFsError(message.readUInt8());
                data.getMspSystemData().setSdcardFreeSize(message.readUInt32());
                data.getMspSystemData().setSdcardTotalSize(message.readUInt32());
                break;

            case MSP_BATTERY_CONFIG:
                data.getMspBatteryData().setVbatMinCellVoltage(message.readUInt8());
                data.getMspBatteryData().setVbatMaxCellVoltage(message.readUInt8());
                data.getMspBatteryData().setVbatWarningCellVoltage(message.readUInt8());
                data.getMspBatteryData().setCapacity(message.readUInt16());
                data.getMspBatteryData().setVoltageMeterSource(message.readUInt8());
                data.getMspBatteryData().setCurrentMeterSource(message.readUInt8());
                break;

            case MSP_VOLTAGE_METER_CONFIG:
                data.getMspBatteryData().getVoltageData().clear();

                if (ltVersion(data, 1.36)) {
                    MspBatteryVoltageData voltageData = new MspBatteryVoltageData();
                    voltageData.setId(0);
                    voltageData.setScale(message.readUInt8());
                    data.getMspBatteryData().setVbatMinCellVoltage(message.readUInt8());
                    data.getMspBatteryData().setVbatMaxCellVoltage(message.readUInt8());
                    data.getMspBatteryData().setVbatWarningCellVoltage(message.readUInt8());

                    if (gtVersion(data, 1.23)) {
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

                break;

            case MSP_CURRENT_METER_CONFIG:
                data.getMspBatteryData().getCurrentData().clear();

                if (ltVersion(data, 1.36))  {
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

                break;

            case MSP_FEATURE_CONFIG:
                int featureConfing = message.readUInt32();
                Log.d(TAG, "Feature - " + featureConfing);


                if (!gtVersion(data, 1.33)) {
                    Log.d(TAG, "BLACKBOX : " + MspUtils.bitCheck(featureConfing, 19));
                }

                if (gtVersion(data, 1.12)) {
                    Log.d(TAG, "CHANNEL_FORWARDING : " + MspUtils.bitCheck(featureConfing, 20));
                }

                if (gtVersion(data, 1.15) && !gtVersion(data, 1.36)) {
                    Log.d(TAG, "FAILSAFE : " + MspUtils.bitCheck(featureConfing, 8));
                }

                if (gtVersion(data, 1.16)) {
                    Log.d(TAG, "TRANSPONDER : " + MspUtils.bitCheck(featureConfing, 21));
                }

                if (!data.getMspSystemData().getBoardFlightControllerVersion().isEmpty()) {
                    if (gtVersion(data, 1.16)) {
                        Log.d(TAG, "AIRMODE : " + MspUtils.bitCheck(featureConfing, 22));

                        if (ltVersion(data, 1.20)) {
                            Log.d(TAG, "SUPEREXPO_RATES : " + MspUtils.bitCheck(featureConfing, 23));

                        } else if (!gtVersion(data, 1.33)) {
                            Log.d(TAG, "SDCARD : " + MspUtils.bitCheck(featureConfing, 23));
                        }
                    }

                    if (gtVersion(data, 1.20)) {
                        Log.d(TAG, "OSD : " + MspUtils.bitCheck(featureConfing, 18));

                        if (!gtVersion(data, 1.35)) {
                            Log.d(TAG, "VTX : " + MspUtils.bitCheck(featureConfing, 24));
                        }
                    }

                    if (gtVersion(data, 1.31)) {
                        Log.d(TAG, "RX_SPI : " + MspUtils.bitCheck(featureConfing, 25));
                        Log.d(TAG, "ESC_SENSOR : " + MspUtils.bitCheck(featureConfing, 27));
                    }

                    if (gtVersion(data, 1.36)) {
                        Log.d(TAG, "ANTI_GRAVITY : " + MspUtils.bitCheck(featureConfing, 28));
                        Log.d(TAG, "DYNAMIC_FILTER : " + MspUtils.bitCheck(featureConfing, 29));
                    }

                    if (!gtVersion(data, 1.36)) {
                        Log.d(TAG, "VBAT : " + MspUtils.bitCheck(featureConfing, 1));
                        Log.d(TAG, "CURRENT_METER : " + MspUtils.bitCheck(featureConfing, 11));
                    }
                }

                break;

            default:
                Log.d(TAG, "Parser for " + messageType.getCode() + " not found");

        }
    }
}
