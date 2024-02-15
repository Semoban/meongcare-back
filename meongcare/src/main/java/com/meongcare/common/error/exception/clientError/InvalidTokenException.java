package com.meongcare.common.error.exception.clientError;

import com.meongcare.common.error.ErrorCode;

public class InvalidTokenException extends BadRequestException {

    public InvalidTokenException(ErrorCode errorCode) {
        super(errorCode);
    }
}
