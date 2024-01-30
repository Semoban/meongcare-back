package com.meongcare.common.error.exception.serverError;

import com.meongcare.common.error.ErrorCode;

public class FailedFileUploadException extends InternalServerException {
    public FailedFileUploadException(ErrorCode errorCode) {
        super(errorCode);
    }
}
