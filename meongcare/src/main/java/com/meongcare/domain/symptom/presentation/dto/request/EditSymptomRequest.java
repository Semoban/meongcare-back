package com.meongcare.domain.symptom.presentation.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

import static com.meongcare.common.DateTimePattern.COMMON_PATTERN;

@Getter
public class EditSymptomRequest {

    @NotNull
    private Long symptomId;

    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = COMMON_PATTERN, timezone = "Asia/Seoul")
    private LocalDateTime dateTime;

    @NotNull
    private String symptomString;

    private String note;

}
