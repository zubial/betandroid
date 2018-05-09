package net.zubial.msprotocol.data;

import java.io.Serializable;

public class MspLiveRcData implements Serializable {

    private static final long serialVersionUID = 1L;

    // MSP_RC
    private Integer id;
    private Integer value;


    public MspLiveRcData() {
        // Default Constructor
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }
}
