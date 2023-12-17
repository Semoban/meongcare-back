package com.meongcare.common.error.exception;

import com.meongcare.common.error.ErrorCode;

public class FailedFileUploadException extends BaseException{
    public FailedFileUploadException(ErrorCode errorCode) {
        super(errorCode);
    }
}
