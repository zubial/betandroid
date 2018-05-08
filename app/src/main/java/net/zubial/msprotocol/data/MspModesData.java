package net.zubial.msprotocol.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MspModesData implements Serializable {

    private static final long serialVersionUID = 1L;

    // MSP_MODE_RANGES
    private List<MspModeData> mspModes;

    // MSP_BOXIDS
    private Map<Integer, Integer> mspModeIds;

    // MSP_BOXNAMES
    private Map<Integer, String> mspModeNames;

    public MspModesData() {
        // Default Constructor
    }

    public List<MspModeData> getMspModes() {
        if (mspModes == null) {
            mspModes = new ArrayList<>();
        }
        return mspModes;
    }

    public Map<Integer, Integer> getMspModeIds() {
        if (mspModeIds == null) {
            mspModeIds = new HashMap<>();
        }
        return mspModeIds;
    }

    public Map<Integer, String> getMspModeNames() {
        if (mspModeNames == null) {
            mspModeNames = new HashMap<>();
        }
        return mspModeNames;
    }
}
