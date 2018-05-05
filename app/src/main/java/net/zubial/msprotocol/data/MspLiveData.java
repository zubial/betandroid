package net.zubial.msprotocol.data;

import java.io.Serializable;

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
    private Double accelerometer0;
    private Double accelerometer1;
    private Double accelerometer2;

    private Double gyroscope0;
    private Double gyroscope1;
    private Double gyroscope2;

    private Double magnetometer0;
    private Double magnetometer1;
    private Double magnetometer2;

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

    public Double getAccelerometer0() {
        return accelerometer0;
    }

    public void setAccelerometer0(Double accelerometer0) {
        this.accelerometer0 = accelerometer0;
    }

    public Double getAccelerometer1() {
        return accelerometer1;
    }

    public void setAccelerometer1(Double accelerometer1) {
        this.accelerometer1 = accelerometer1;
    }

    public Double getAccelerometer2() {
        return accelerometer2;
    }

    public void setAccelerometer2(Double accelerometer2) {
        this.accelerometer2 = accelerometer2;
    }

    public Double getGyroscope0() {
        return gyroscope0;
    }

    public void setGyroscope0(Double gyroscope0) {
        this.gyroscope0 = gyroscope0;
    }

    public Double getGyroscope1() {
        return gyroscope1;
    }

    public void setGyroscope1(Double gyroscope1) {
        this.gyroscope1 = gyroscope1;
    }

    public Double getGyroscope2() {
        return gyroscope2;
    }

    public void setGyroscope2(Double gyroscope2) {
        this.gyroscope2 = gyroscope2;
    }

    public Double getMagnetometer0() {
        return magnetometer0;
    }

    public void setMagnetometer0(Double magnetometer0) {
        this.magnetometer0 = magnetometer0;
    }

    public Double getMagnetometer1() {
        return magnetometer1;
    }

    public void setMagnetometer1(Double magnetometer1) {
        this.magnetometer1 = magnetometer1;
    }

    public Double getMagnetometer2() {
        return magnetometer2;
    }

    public void setMagnetometer2(Double magnetometer2) {
        this.magnetometer2 = magnetometer2;
    }
}
