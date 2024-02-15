package com.meongcare.common.error;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class ErrorResponse {
    private final Integer statusCode;
    private final String message;


    public ErrorResponse(HttpStatus httpStatus, String message) {
        this.statusCode = httpStatus.value();
        this.message = message;
    }
}
