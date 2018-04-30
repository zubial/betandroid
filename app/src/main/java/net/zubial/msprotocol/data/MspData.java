package net.zubial.msprotocol.data;

import java.io.Serializable;

public class MspData implements Serializable {

    private static final long serialVersionUID = 1L;

    private MspSystemData mspSystemData = new MspSystemData();
    private MspBatteryData mspBatteryData = new MspBatteryData();

    public MspData() {
        // Default Constructor
    }

    public MspSystemData getMspSystemData() {
        return mspSystemData;
    }

    public MspBatteryData getMspBatteryData() {
        return mspBatteryData;
    }
}
