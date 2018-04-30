package com.betandroid.msprotocol.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MspBatteryData implements Serializable {

    private static final long serialVersionUID = 1L;

    // MSP_BATTERY_CONFIG
    private Integer vbatMinCellVoltage;
    private Integer vbatMaxCellVoltage;
    private Integer vbatWarningCellVoltage;
    private Integer capacity;
    private Integer voltageMeterSource;
    private Integer currentMeterSource;

    // MSP_CURRENT_METER_CONFIG
    private List<MspBatteryCurrentData> currentData;


    // MSP_VOLTAGE_METER_CONFIG
    private List<MspBatteryVoltageData> voltageData;

    public MspBatteryData() {
        // Default Constructor
    }

    public Integer getVbatMinCellVoltage() {
        return vbatMinCellVoltage;
    }

    public void setVbatMinCellVoltage(Integer vbatMinCellVoltage) {
        this.vbatMinCellVoltage = vbatMinCellVoltage;
    }

    public Integer getVbatMaxCellVoltage() {
        return vbatMaxCellVoltage;
    }

    public void setVbatMaxCellVoltage(Integer vbatMaxCellVoltage) {
        this.vbatMaxCellVoltage = vbatMaxCellVoltage;
    }

    public Integer getVbatWarningCellVoltage() {
        return vbatWarningCellVoltage;
    }

    public void setVbatWarningCellVoltage(Integer vbatWarningCellVoltage) {
        this.vbatWarningCellVoltage = vbatWarningCellVoltage;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public Integer getVoltageMeterSource() {
        return voltageMeterSource;
    }

    public void setVoltageMeterSource(Integer voltageMeterSource) {
        this.voltageMeterSource = voltageMeterSource;
    }

    public Integer getCurrentMeterSource() {
        return currentMeterSource;
    }

    public void setCurrentMeterSource(Integer currentMeterSource) {
        this.currentMeterSource = currentMeterSource;
    }

    public List<MspBatteryVoltageData> getVoltageData() {
        if (voltageData == null) {
            voltageData = new ArrayList<>();
        }
        return voltageData;
    }

    public List<MspBatteryCurrentData> getCurrentData() {
        if (currentData == null) {
            currentData = new ArrayList<>();
        }
        return currentData;
    }
}
