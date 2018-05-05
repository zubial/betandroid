package net.zubial.msprotocol;

import android.content.Context;

import net.zubial.msprotocol.data.MspBatteryData;
import net.zubial.msprotocol.enums.MspMessageTypeEnum;
import net.zubial.msprotocol.io.MspBuffer;

import java.util.ArrayList;

public class MspService extends MspServiceAbstract {

    private static MspService mspService;

    private MspService(Context applicationContext) {
        super(applicationContext);
    }

    public static MspService getInstance() {
        return mspService;
    }

    public static MspService getInstance(Context applicationContext) {
        if (mspService == null) {
            mspService = new MspService(applicationContext);
        }
        return mspService;
    }

    public void loadHandshake() {
        ArrayList<MspMessageTypeEnum> listCommand = new ArrayList<>();
        listCommand.add(MspMessageTypeEnum.MSP_API_VERSION);
        listCommand.add(MspMessageTypeEnum.MSP_FC_VARIANT);
        listCommand.add(MspMessageTypeEnum.MSP_FC_VERSION);
        listCommand.add(MspMessageTypeEnum.MSP_BOARD_INFO);
        listCommand.add(MspMessageTypeEnum.MSP_BUILD_INFO);
        listCommand.add(MspMessageTypeEnum.MSP_NAME);

        sendMultiCommand(listCommand);
    }

    public void loadSystemData() {
        ArrayList<MspMessageTypeEnum> listCommand = new ArrayList<>();
        listCommand.add(MspMessageTypeEnum.MSP_STATUS_EX);
        listCommand.add(MspMessageTypeEnum.MSP_SDCARD_SUMMARY);

        sendMultiCommand(listCommand);
    }

    public void loadFeaturesData() {
        ArrayList<MspMessageTypeEnum> listCommand = new ArrayList<>();
        listCommand.add(MspMessageTypeEnum.MSP_FEATURE_CONFIG);

        sendMultiCommand(listCommand);
    }

    public void loadBatteryData() {
        ArrayList<MspMessageTypeEnum> listCommand = new ArrayList<>();
        listCommand.add(MspMessageTypeEnum.MSP_BATTERY_CONFIG);
        listCommand.add(MspMessageTypeEnum.MSP_VOLTAGE_METER_CONFIG);
        listCommand.add(MspMessageTypeEnum.MSP_CURRENT_METER_CONFIG);

        sendMultiCommand(listCommand);
    }

    public void loadLiveData() {
        ArrayList<MspMessageTypeEnum> listCommand = new ArrayList<>();
        listCommand.add(MspMessageTypeEnum.MSP_BOXNAMES);
        listCommand.add(MspMessageTypeEnum.MSP_STATUS_EX);
        listCommand.add(MspMessageTypeEnum.MSP_ANALOG);

        sendMultiCommand(listCommand);
    }


    public void executeAccCalibration() {
        sendCommand(MspMessageTypeEnum.MSP_ACC_CALIBRATION);
    }

    public void setBoardName(String boardName) {
        sendMessage(MspMessageTypeEnum.MSP_SET_NAME, boardName.getBytes());
    }

    public void setBatteryConfig(MspBatteryData batteryData) {
        MspBuffer buffer = new MspBuffer(7);
        buffer.writeInt8(batteryData.getVbatMinCellVoltage());
        buffer.writeInt8(batteryData.getVbatMaxCellVoltage());
        buffer.writeInt8(batteryData.getVbatWarningCellVoltage());
        buffer.writeInt16(batteryData.getCapacity());
        buffer.writeInt8(batteryData.getVoltageMeterSource());
        buffer.writeInt8(batteryData.getCurrentMeterSource());

        sendMessage(MspMessageTypeEnum.MSP_SET_BATTERY_CONFIG, buffer.getMessage());
    }

    public void setFeatures(int featureMask) {
        MspBuffer buffer = new MspBuffer(4);
        buffer.writeInt32(featureMask);

        sendMessage(MspMessageTypeEnum.MSP_SET_FEATURE_CONFIG, buffer.getMessage());
    }
}
