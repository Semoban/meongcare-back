package com.meongcare.common.error.exception;

import com.meongcare.common.error.ErrorCode;

public class UnauthorizedException extends BaseException{
    public UnauthorizedException(ErrorCode errorCode) {
        super(errorCode);
    }
}
