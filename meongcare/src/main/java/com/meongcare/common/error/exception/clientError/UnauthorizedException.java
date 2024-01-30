package com.meongcare.common.error.exception.clientError;

import com.meongcare.common.error.ErrorCode;

public class UnauthorizedException extends BadRequestException {
    public UnauthorizedException(ErrorCode errorCode) {
        super(errorCode);
    }
}
