package net.zubial.msprotocol.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MspData implements Serializable {

    private static final long serialVersionUID = 1L;

    private MspSystemData mspSystemData = new MspSystemData();
    private MspBatteryData mspBatteryData = new MspBatteryData();
    private MspLiveData mspLiveData = new MspLiveData();

    private int mspFeaturesMask;
    private List<MspFeatureData> mspFeatures;

    private MspModesData mspModesData = new MspModesData();

    public MspData() {
        // Default Constructor
    }

    public MspSystemData getMspSystemData() {
        return mspSystemData;
    }

    public MspBatteryData getMspBatteryData() {
        return mspBatteryData;
    }

    public List<MspFeatureData> getMspFeatures() {
        if (mspFeatures == null) {
            mspFeatures = new ArrayList<>();
        }
        return mspFeatures;
    }

    public List<MspFeatureData> getMspFeaturesSelectable() {
        List<MspFeatureData> mspFeaturesFilter = new ArrayList<>();

        if (mspFeatures != null && !mspFeatures.isEmpty()) {
            for (MspFeatureData feature : mspFeatures) {
                if (feature.getFeature().isSelectable()) {
                    mspFeaturesFilter.add(feature);
                }
            }
        }

        return mspFeaturesFilter;
    }

    public int getMspFeaturesMask() {
        return mspFeaturesMask;
    }

    public void setMspFeaturesMask(int mspFeaturesMask) {
        this.mspFeaturesMask = mspFeaturesMask;
    }

    public MspLiveData getMspLiveData() {
        return mspLiveData;
    }

    public MspModesData getMspModesData() {
        return mspModesData;
    }

    public void setMspModesData(MspModesData mspModesData) {
        this.mspModesData = mspModesData;
    }
}
