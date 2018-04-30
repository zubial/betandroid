package net.zubial.msprotocol.data;

import java.io.Serializable;

public class MspBatteryCurrentData implements Serializable {

    private static final long serialVersionUID = 1L;

    // MSP_CURRENT_METER_CONFIG
    private Integer id;
    private Integer sensorType;
    private Integer scale;
    private Integer offset;

    public MspBatteryCurrentData() {
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

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }
}
