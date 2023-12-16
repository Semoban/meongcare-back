package com.meongcare.common.error;

import lombok.Data;

@Data
public class ErrorResponse {
    private final Integer statusCode;
    private final String message;

    public ErrorResponse(ErrorCode errorCode) {
        this.statusCode = errorCode.getHttpStatus().value();
        this.message = errorCode.getMessage();
    }
}
