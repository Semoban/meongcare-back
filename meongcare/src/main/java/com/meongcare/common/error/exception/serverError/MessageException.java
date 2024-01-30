package com.meongcare.common.error.exception.serverError;

import com.meongcare.common.error.ErrorCode;

public class MessageException extends InternalServerException{

    public MessageException(ErrorCode errorCode) {
        super(errorCode);
    }
}
