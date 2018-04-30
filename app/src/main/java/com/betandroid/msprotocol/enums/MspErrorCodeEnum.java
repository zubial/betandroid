package com.betandroid.msprotocol.enums;

public enum MspErrorCodeEnum {

	// COMMON
	COMMON_OK("Operation Done"),
    COMMON_PARAM_MISSING("Parameters missing : %s"),

	MSP_PROTOCOL_CRC_MISMATCH("CRC - Checksum mismatch");


    private final String label;

	MspErrorCodeEnum(final String label) {
		this.label = label;
	}

	public String label() {
		return label;
	}

	public String label(final Object... args) {
		return String.format(this.label, args);
	}
}