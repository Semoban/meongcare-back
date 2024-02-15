package com.meongcare.common.error.exception.clientError;

import com.meongcare.common.error.ErrorCode;
import com.meongcare.common.error.exception.BaseException;

public class BadRequestException extends BaseException {
    public BadRequestException(ErrorCode errorCode) {
        super(errorCode);
    }
}
