package com.meongcare.common.error;

import com.meongcare.common.error.exception.clientError.BadRequestException;
import com.meongcare.common.error.exception.serverError.InternalServerException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(BindException.class)
    public ResponseEntity<ErrorResponse> bindException(BindingResult bindingResult) {
        StringBuilder reason = new StringBuilder();
        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            String errorMessage = fieldError.getField() + " : " + fieldError.getDefaultMessage();
            reason.append(errorMessage).append(", ");
        }
        log.warn("ValidationException - {}", reason);
        final ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST, String.valueOf(reason));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(BadRequestException.class)
    protected ResponseEntity<ErrorResponse> badRequestException(BadRequestException e) {
        log.warn("[{}] {} ({})", e.getClass().getSimpleName(), e.getMessage(), e.getStackTrace()[0]);
        final ErrorResponse errorResponse = new ErrorResponse(e.getHttpStatus(), e.getMessage());
        return ResponseEntity.status(e.getHttpStatus()).body(errorResponse);
    }

    @ExceptionHandler(InternalServerException.class)
    protected ResponseEntity<ErrorResponse> internalServerException(InternalServerException e) {
        log.error("[{}] {} ({})", e.getClass().getSimpleName(), e.getMessage(), e.getStackTrace()[0]);
        final ErrorResponse errorResponse = new ErrorResponse(e.getHttpStatus(), e.getMessage());
        return ResponseEntity.status(e.getHttpStatus()).body(errorResponse);
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ErrorResponse> runtimeException(Exception e) {
        log.error("[{}] {} ({})", e.getClass().getSimpleName(), e.getMessage(), e.getStackTrace()[0]);
        final ErrorResponse errorResponse = new ErrorResponse(ErrorCode.INTERNAL_SERVER_ERROR);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }

}
