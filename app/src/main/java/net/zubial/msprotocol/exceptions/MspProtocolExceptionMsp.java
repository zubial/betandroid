package net.zubial.msprotocol.exceptions;

import net.zubial.msprotocol.enums.MspErrorCodeEnum;

public class MspProtocolExceptionMsp extends MspBaseException {

    public MspProtocolExceptionMsp() {
    }

    public MspProtocolExceptionMsp(String errorMessage) {
        super(errorMessage);
    }

    public MspProtocolExceptionMsp(MspErrorCodeEnum errorCodeEnum) {
        super(errorCodeEnum);
    }

    public MspProtocolExceptionMsp(MspErrorCodeEnum errorCodeEnum, Throwable runtimeError, Object... args) {
        super(errorCodeEnum, runtimeError, args);
    }
}
