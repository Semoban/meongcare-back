package com.meongcare.common.error;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum ErrorCode {

    // 4xx
    S3_FOLDER_PATH_NOT_FOUND(HttpStatus.BAD_REQUEST, "존재하지 않는 S3 폴더경로 입니다."),
    SYMPTOM_ENTITY_NOT_FOUND(HttpStatus.BAD_REQUEST, "존재하지 않는 이상증상 ID 입니다."),
    FEED_RECORD_ENTITY_NOT_FOUND(HttpStatus.BAD_REQUEST, "존재하지 않는 사료 기록 ID 입니다."),
    FEED_ENTITY_NOT_FOUND(HttpStatus.BAD_REQUEST, "존재하지 않는 사료 ID 입니다."),
    EXCRETA_ENTITY_NOT_FOUND(HttpStatus.BAD_REQUEST, "존재하지 않는 대소변 ID 입니다."),
    SUPPLEMENTS_RECORD_ENTITY_NOT_FOUND(HttpStatus.BAD_REQUEST, "존재하지 않는 영양제 기록 ID 입니다."),
    SUPPLEMENTS_TIME_ENTITY_NOT_FOUND(HttpStatus.BAD_REQUEST, "존재하지 않는 영양제 시간 ID 입니다."),
    SUPPLEMENTS_ENTITY_NOT_FOUND(HttpStatus.BAD_REQUEST, "존재하지 않는 영양제 ID 입니다."),
    NOTICE_ENTITY_NOT_FOUND(HttpStatus.BAD_REQUEST, "존재하지 않는 공지사항 ID 입니다."),
    MEDICAL_RECORD_ENTITY_NOT_FOUND(HttpStatus.BAD_REQUEST, "존재하지 않는 진료기록 ID 입니다."),
    MEMBER_NOT_FOUND(HttpStatus.BAD_REQUEST, "존재하지 않는 사용자 입니다."),
    DOG_ENTITY_NOT_FOUND(HttpStatus.BAD_REQUEST, "존재하지 않는 강아지 ID 입니다."),
    WEIGHT_ENTITY_NOT_FOUND(HttpStatus.BAD_REQUEST, "존재하지 않는 몸무게 데이터 입니다."),
    REVOKE_MEMBER_NOT_ALLOWED_LOGIN(HttpStatus.BAD_REQUEST, "탈퇴 유저는 90일 간 회원 가입할 수 없습니다."),

    INVALID_ACCESS_TOKEN(HttpStatus.UNAUTHORIZED, "유효하지 않은 액세스 토큰입니다."),
    INVALID_REFRESH_TOKEN(HttpStatus.UNAUTHORIZED, "유효하지 않은 리프레시 토큰입니다."),
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "유효하지 않은 토큰입니다."), //토큰 파싱할 때 사용

    BAD_REQUEST(HttpStatus.BAD_REQUEST, "클라이언트 에러입니다"),

    // 5xx
    FAILED_FILE_UPLOAD(HttpStatus.INTERNAL_SERVER_ERROR, "파일이 업로드되지 않았습니다."),
    FAILED_MESSAGE_SEND(HttpStatus.INTERNAL_SERVER_ERROR, "알림 메세지 보내기를 실패했습니다"),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 에러입니다."),;

    private final HttpStatus httpStatus;
    private final String message;
}
