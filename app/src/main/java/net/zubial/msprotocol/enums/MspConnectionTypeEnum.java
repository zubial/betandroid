package net.zubial.msprotocol.enums;

public enum MspConnectionTypeEnum {

    BLUETOOTH("Bluetooth connection"),
    USB("USB Connection");


    private final String label;

    MspConnectionTypeEnum(final String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
