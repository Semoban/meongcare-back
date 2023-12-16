package com.meongcare.common.error;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum ErrorCode {


    INVALID_ACCESS_TOKEN(HttpStatus.UNAUTHORIZED, "유효하지 않은 토큰입니다.");

    private final HttpStatus httpStatus;
    private final String message;
}
