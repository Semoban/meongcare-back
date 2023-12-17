package com.meongcare.common.error;

import com.meongcare.common.error.exception.EntityNotFoundException;
import com.meongcare.common.error.exception.FailedFileUploadException;
import com.meongcare.common.error.exception.InvalidTokenException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    protected ResponseEntity<ErrorResponse> runtimeException(RuntimeException e) {
        e.printStackTrace();
        final ErrorResponse errorResponse = new ErrorResponse(ErrorCode.INTERNAL_SERVER_ERROR);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }

    @ExceptionHandler(InvalidTokenException.class)
    protected ResponseEntity<ErrorResponse> invalidTokenException(InvalidTokenException e) {
        e.printStackTrace();
        final ErrorResponse errorResponse = new ErrorResponse(e.getHttpStatus(), e.getMessage());
        return ResponseEntity.status(e.getHttpStatus()).body(errorResponse);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    protected ResponseEntity<ErrorResponse> entityNotFoundException(EntityNotFoundException e) {
        e.printStackTrace();
        final ErrorResponse errorResponse = new ErrorResponse(e.getHttpStatus(), e.getMessage());
        return ResponseEntity.status(e.getHttpStatus()).body(errorResponse);
    }

    @ExceptionHandler(FailedFileUploadException.class)
    protected ResponseEntity<ErrorResponse> failedFileUploadException(FailedFileUploadException e) {
        e.printStackTrace();
        final ErrorResponse errorResponse = new ErrorResponse(e.getHttpStatus(), e.getMessage());
        return ResponseEntity.status(e.getHttpStatus()).body(errorResponse);
    }
}
