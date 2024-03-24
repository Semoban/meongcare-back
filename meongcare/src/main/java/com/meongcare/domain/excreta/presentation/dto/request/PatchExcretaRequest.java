package com.meongcare.domain.excreta.presentation.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

import static com.meongcare.common.DateTimePattern.COMMON_PATTERN;

@Getter
public class PatchExcretaRequest {

    @NotNull
    private Long excretaId;

    @NotNull
    private String excretaString;

    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = COMMON_PATTERN, timezone = "Asia/Seoul")
    private LocalDateTime dateTime;

    @Schema(description = "이미지 S3 링크")
    private String imageURL;
}
