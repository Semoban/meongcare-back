package com.meongcare.common.error;

import com.meongcare.common.error.exception.EntityNotFoundException;
import com.meongcare.common.error.exception.FailedFileUploadException;
import com.meongcare.common.error.exception.InvalidTokenException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {


    @ExceptionHandler(InvalidTokenException.class)
    protected ResponseEntity<ErrorResponse> invalidTokenException(InvalidTokenException e) {
        e.printStackTrace();
        log.warn("{} - {}", e.getClass().getSimpleName(), e.getMessage());
        final ErrorResponse errorResponse = new ErrorResponse(e.getHttpStatus(), e.getMessage());
        return ResponseEntity.status(e.getHttpStatus()).body(errorResponse);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    protected ResponseEntity<ErrorResponse> entityNotFoundException(EntityNotFoundException e) {
        e.printStackTrace();
        log.warn("{} - {}", e.getClass().getSimpleName(), e.getMessage());
        final ErrorResponse errorResponse = new ErrorResponse(e.getHttpStatus(), e.getMessage());
        return ResponseEntity.status(e.getHttpStatus()).body(errorResponse);
    }

    @ExceptionHandler(FailedFileUploadException.class)
    protected ResponseEntity<ErrorResponse> failedFileUploadException(FailedFileUploadException e) {
        e.printStackTrace();
        log.warn("{} - {}", e.getClass().getSimpleName(), e.getMessage());
        final ErrorResponse errorResponse = new ErrorResponse(e.getHttpStatus(), e.getMessage());
        return ResponseEntity.status(e.getHttpStatus()).body(errorResponse);
    }

    @ExceptionHandler(RuntimeException.class)
    protected ResponseEntity<ErrorResponse> runtimeException(RuntimeException e) {
        e.printStackTrace();
        log.warn("{} - {}", e.getClass().getSimpleName(), e.getMessage());
        final ErrorResponse errorResponse = new ErrorResponse(ErrorCode.INTERNAL_SERVER_ERROR);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ErrorResponse> exception(Exception e) {
        e.printStackTrace();
        log.warn("{} - {}", e.getClass().getSimpleName(), e.getMessage());
        final ErrorResponse errorResponse = new ErrorResponse(ErrorCode.INTERNAL_SERVER_ERROR);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }

    //validation 에러
    @ExceptionHandler({BindException.class, MethodArgumentNotValidException.class})
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
}
