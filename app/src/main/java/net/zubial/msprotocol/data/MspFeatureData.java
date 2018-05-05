package net.zubial.msprotocol.data;

import net.zubial.msprotocol.enums.MspFeatureEnum;

import java.io.Serializable;

public class MspFeatureData implements Serializable {

    private static final long serialVersionUID = 1L;

    private MspFeatureEnum feature;
    private Boolean enable;

    public MspFeatureData() {
        // Default Constructor
    }

    public MspFeatureData(MspFeatureEnum feature, Boolean enable) {
        this.feature = feature;
        this.enable = enable;
    }

    public MspFeatureEnum getFeature() {
        return feature;
    }

    public void setFeature(MspFeatureEnum feature) {
        this.feature = feature;
    }

    public Boolean isEnable() {
        return enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }
}
