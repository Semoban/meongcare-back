package com.meongcare.common.error.exception;

import com.meongcare.common.error.ErrorCode;

public class InvalidTokenException extends BaseException{

    public InvalidTokenException(ErrorCode errorCode) {
        super(errorCode);
    }
}
