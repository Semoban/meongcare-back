package com.meongcare.common.error.exception;

import com.meongcare.common.error.ErrorCode;

public class EntityNotFoundException extends BaseException{
    public EntityNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }
}
