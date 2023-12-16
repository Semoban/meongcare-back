package com.meongcare.common.error;

import lombok.Data;

@Data
public class ErrorResponse {
    private final Integer errorCode;
    private final String message;
}
