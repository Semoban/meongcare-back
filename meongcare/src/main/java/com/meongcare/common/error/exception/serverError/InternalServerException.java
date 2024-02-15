package com.meongcare.common.error.exception.serverError;

import com.meongcare.common.error.ErrorCode;
import com.meongcare.common.error.exception.BaseException;

public class InternalServerException extends BaseException {
    public InternalServerException(ErrorCode errorCode) {
        super(errorCode);
    }
}
