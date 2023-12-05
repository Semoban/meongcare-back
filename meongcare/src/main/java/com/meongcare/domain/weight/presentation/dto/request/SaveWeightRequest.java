package com.meongcare.domain.weight.presentation.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

import static com.meongcare.common.DateTimePattern.DATE_PATTERN;

@Getter
public class SaveWeightRequest {

    @Schema(description = "개 ID", example = "1")
    @NotNull
    private Long dogId;

    @Schema(description = "오늘 DATE")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_PATTERN, timezone = "Asia/Seoul")
    private LocalDate date;

    @Schema(description = "몸무게 수정 시 수정한 몸무게", example = "23.5")
    private Double kg;
}
