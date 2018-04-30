package net.zubial.msprotocol.exceptions;

import net.zubial.msprotocol.enums.MspErrorCodeEnum;

public abstract class MspBaseException extends Exception {

    private static final String DEFAULT_MESSAGE = "MspBaseException";
    private static final String DEFAULT_CODE = "-1";

    private final String errorCode;

    public MspBaseException() {
        super(DEFAULT_MESSAGE);

        this.errorCode = DEFAULT_CODE;
    }

    public MspBaseException(final String errorMessage) {
        super(errorMessage);

        this.errorCode = DEFAULT_CODE;
    }

    public MspBaseException(final MspErrorCodeEnum errorCodeEnum) {
        super(errorCodeEnum.label());

        this.errorCode = errorCodeEnum.name();
    }

    public MspBaseException(final MspErrorCodeEnum errorCodeEnum, final Throwable runtimeError, final Object... args) {
        super(errorCodeEnum.label(args), runtimeError);

        this.errorCode = errorCodeEnum.name();
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getErrorMessage() {
        return getMessage();
    }
}
