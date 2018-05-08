package net.zubial.msprotocol.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MspLiveData implements Serializable {

    private static final long serialVersionUID = 1L;

    // MSP_STATUS_EX
    private Integer statusCycleTime;
    private Integer statusI2cError;
    private Integer statusCpuload;

    // MSP_BATTERY_CONFIG
    private Double voltage;
    private Integer mAhDrawn;
    private Integer rssi;
    private Double amperage;
    private Integer voltageMeterSource;
    private Integer currentMeterSource;

    // MSP_RAW_IMU
    private Double accelerometerX;
    private Double accelerometerY;
    private Double accelerometerZ;

    private Double gyroscopeX;
    private Double gyroscopeY;
    private Double gyroscopeZ;

    private Double magnetometerX;
    private Double magnetometerY;
    private Double magnetometerZ;

    // MSP_ATTITUDE
    private Double kinematicsX;
    private Double kinematicsY;
    private Double kinematicsZ;

    // MSP_ALTITUDE
    private Double altitude;

    // MSP_RC
    private List<MspLiveRcData> mspLiveRc;

    public MspLiveData() {
        // Default Constructor
    }

    public Integer getStatusCycleTime() {
        return statusCycleTime;
    }

    public void setStatusCycleTime(Integer statusCycleTime) {
        this.statusCycleTime = statusCycleTime;
    }

    public Integer getStatusI2cError() {
        return statusI2cError;
    }

    public void setStatusI2cError(Integer statusI2cError) {
        this.statusI2cError = statusI2cError;
    }

    public Integer getStatusCpuload() {
        return statusCpuload;
    }

    public void setStatusCpuload(Integer statusCpuload) {
        this.statusCpuload = statusCpuload;
    }

    public Double getVoltage() {
        return voltage;
    }

    public void setVoltage(Double voltage) {
        this.voltage = voltage;
    }

    public Integer getmAhDrawn() {
        return mAhDrawn;
    }

    public void setmAhDrawn(Integer mAhDrawn) {
        this.mAhDrawn = mAhDrawn;
    }

    public Integer getRssi() {
        return rssi;
    }

    public void setRssi(Integer rssi) {
        this.rssi = rssi;
    }

    public Double getAmperage() {
        return amperage;
    }

    public void setAmperage(Double amperage) {
        this.amperage = amperage;
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

    public Double getAccelerometerX() {
        return accelerometerX;
    }

    public void setAccelerometerX(Double accelerometerX) {
        this.accelerometerX = accelerometerX;
    }

    public Double getAccelerometerY() {
        return accelerometerY;
    }

    public void setAccelerometerY(Double accelerometerY) {
        this.accelerometerY = accelerometerY;
    }

    public Double getAccelerometerZ() {
        return accelerometerZ;
    }

    public void setAccelerometerZ(Double accelerometerZ) {
        this.accelerometerZ = accelerometerZ;
    }

    public Double getGyroscopeX() {
        return gyroscopeX;
    }

    public void setGyroscopeX(Double gyroscopeX) {
        this.gyroscopeX = gyroscopeX;
    }

    public Double getGyroscopeY() {
        return gyroscopeY;
    }

    public void setGyroscopeY(Double gyroscopeY) {
        this.gyroscopeY = gyroscopeY;
    }

    public Double getGyroscopeZ() {
        return gyroscopeZ;
    }

    public void setGyroscopeZ(Double gyroscopeZ) {
        this.gyroscopeZ = gyroscopeZ;
    }

    public Double getMagnetometerX() {
        return magnetometerX;
    }

    public void setMagnetometerX(Double magnetometerX) {
        this.magnetometerX = magnetometerX;
    }

    public Double getMagnetometerY() {
        return magnetometerY;
    }

    public void setMagnetometerY(Double magnetometerY) {
        this.magnetometerY = magnetometerY;
    }

    public Double getMagnetometerZ() {
        return magnetometerZ;
    }

    public void setMagnetometerZ(Double magnetometerZ) {
        this.magnetometerZ = magnetometerZ;
    }

    public Double getKinematicsX() {
        return kinematicsX;
    }

    public void setKinematicsX(Double kinematicsX) {
        this.kinematicsX = kinematicsX;
    }

    public Double getKinematicsY() {
        return kinematicsY;
    }

    public void setKinematicsY(Double kinematicsY) {
        this.kinematicsY = kinematicsY;
    }

    public Double getKinematicsZ() {
        return kinematicsZ;
    }

    public void setKinematicsZ(Double kinematicsZ) {
        this.kinematicsZ = kinematicsZ;
    }

    public Double getAltitude() {
        return altitude;
    }

    public void setAltitude(Double altitude) {
        this.altitude = altitude;
    }

    public List<MspLiveRcData> getMspLiveRc() {
        if (mspLiveRc == null) {
            mspLiveRc = new ArrayList<>();
        }
        return mspLiveRc;
    }
}
