package net.zubial.msprotocol.data;

import net.zubial.msprotocol.enums.MspRcChannelEnum;

import java.io.Serializable;

public class MspLiveRcData implements Serializable {

    private static final long serialVersionUID = 1L;

    // MSP_RC
    private Integer id;
    private MspRcChannelEnum channel;
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

    public MspRcChannelEnum getChannel() {
        return channel;
    }

    public void setChannel(MspRcChannelEnum channel) {
        this.channel = channel;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }
}
