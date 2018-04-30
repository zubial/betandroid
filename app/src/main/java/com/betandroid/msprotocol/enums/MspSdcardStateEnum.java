package com.betandroid.msprotocol.enums;

public enum MspSdcardStateEnum {
    SDCARD_STATE_NOT_PRESENT (0),
    SDCARD_STATE_FATAL       (1),
    SDCARD_STATE_CARD_INIT   (2),
    SDCARD_STATE_FS_INIT     (3),
    SDCARD_STATE_READY       (4);

    private final int code;

    MspSdcardStateEnum(final int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public static MspSdcardStateEnum findByCode(final int code) {
        for (final MspSdcardStateEnum e : MspSdcardStateEnum.values()) {
            if (e.getCode() == code) {
                return e;
            }
        }
        return null;
    }
}
