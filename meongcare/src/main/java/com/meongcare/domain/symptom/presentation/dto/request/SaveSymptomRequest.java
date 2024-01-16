package com.meongcare.domain.symptom.presentation.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

import static com.meongcare.common.DateTimePattern.COMMON_PATTERN;

@Getter
public class SaveSymptomRequest {

    @Schema(description = "개 아이디", example = "1")
    @NotNull
    private Long dogId;

    @Schema(description = "이상증상 문자열", example = "weightLoss")
    @NotNull
    private String symptomString;

    @Schema(description = "이상증상 커스텀 시 기록", example = "고열 및 기침을 함")
    private String note;

    @Schema(description = "기록날짜", example = "2023-03-05T08:11:12")
    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = COMMON_PATTERN, timezone = "Asia/Seoul")
    private LocalDateTime dateTime;
}
