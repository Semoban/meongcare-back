package com.meongcare.domain.symptom.presentation.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

import static com.meongcare.common.DateTimePattern.COMMON_PATTERN;

@Getter
public class EditSymptomRequest {

    @Schema(description = "이상증상 ID", example = "1")
    @NotNull
    private Long symptomId;

    @Schema(description = "수정하려는 시간", example = "2023-08-13T11:00:12")
    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = COMMON_PATTERN, timezone = "Asia/Seoul")
    private LocalDateTime dateTime;

    @Schema(description = "수정하려는 이상증상 문자열", example = "COUGH")
    @NotNull
    private String symptomString;

    @Schema(description = "기타인 경우 수정하려는 메모", example = "기침과 고열을 느끼다 탈진함")
    private String note;

}
