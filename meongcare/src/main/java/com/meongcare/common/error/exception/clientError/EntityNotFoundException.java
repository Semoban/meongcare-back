package com.meongcare.common.error.exception.clientError;

import com.meongcare.common.error.ErrorCode;

public class EntityNotFoundException extends BadRequestException {
    public EntityNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }
}
