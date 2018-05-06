package net.zubial.msprotocol.data;

import java.io.Serializable;

public class MspModeData implements Serializable {

    private static final long serialVersionUID = 1L;

    // MSP_MODE_RANGES
    private Integer id;
    private Integer index;
    private String modeName;
    private Integer auxChannel;
    private Integer rangeStart;
    private Integer rangeEnd;
    private Boolean isEnable;

    public MspModeData() {
        // Default Constructor
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public String getModeName() {
        return modeName;
    }

    public void setModeName(String modeName) {
        this.modeName = modeName;
    }

    public Integer getAuxChannel() {
        return auxChannel;
    }

    public void setAuxChannel(Integer auxChannel) {
        this.auxChannel = auxChannel;
    }

    public Integer getRangeStart() {
        return rangeStart;
    }

    public void setRangeStart(Integer rangeStart) {
        this.rangeStart = rangeStart;
    }

    public Integer getRangeEnd() {
        return rangeEnd;
    }

    public void setRangeEnd(Integer rangeEnd) {
        this.rangeEnd = rangeEnd;
    }

    public Boolean getEnable() {
        return isEnable;
    }

    public void setEnable(Boolean enable) {
        isEnable = enable;
    }
}
