package com.meongcare.common.error.exception;

import com.meongcare.common.error.ErrorCode;

public class InvalidTokenException extends BaseException{

    public InvalidTokenException() {
        super(ErrorCode.INVALID_ACCESS_TOKEN);
    }
}
