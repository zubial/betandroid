package net.zubial.msprotocol.data;

import java.io.Serializable;

public class MspBatteryVoltageData implements Serializable {

    private static final long serialVersionUID = 1L;

    // MSP_VOLTAGE_METER_CONFIG
    private Integer id;
    private Integer sensorType;
    private Integer scale;
    private Integer vbatResdivVal;
    private Integer vbatResdivMultiplier;

    public MspBatteryVoltageData() {
        // Default Constructor
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSensorType() {
        return sensorType;
    }

    public void setSensorType(Integer sensorType) {
        this.sensorType = sensorType;
    }

    public Integer getScale() {
        return scale;
    }

    public void setScale(Integer scale) {
        this.scale = scale;
    }

    public Integer getVbatResdivVal() {
        return vbatResdivVal;
    }

    public void setVbatResdivVal(Integer vbatResdivVal) {
        this.vbatResdivVal = vbatResdivVal;
    }

    public Integer getVbatResdivMultiplier() {
        return vbatResdivMultiplier;
    }

    public void setVbatResdivMultiplier(Integer vbatResdivMultiplier) {
        this.vbatResdivMultiplier = vbatResdivMultiplier;
    }
}
