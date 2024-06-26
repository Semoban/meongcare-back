package com.meongcare.domain.dog.presentation.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
public class PutDogRequest {
    @Schema(description = "강아지 이름", example = "김먼지")
    @NotNull
    private String name;

    @Schema(description = "강아지 종류", example = "푸들")
    @NotNull
    private String type;

    @Schema(description = "강아지 성별 (male, female)", example = "male")
    @NotNull
    private String sex;

    @Schema(description = "강아지 생일", example = "2023-08-13")
    private LocalDate birthDate;

    @Schema(description = "중성화 수술 여부", example = "false")
    @NotNull
    private boolean castrate;

    @Schema(description = "강아지 체중", example = "5.10")
    @NotNull
    private double weight;

    @Schema(description = "강아지 등 둘레", example = "10.9")
    @NotNull
    private double backRound;

    @Schema(description = "강아지 목 둘레", example = "3.3")
    @NotNull
    private double neckRound;

    @Schema(description = "강아지 가슴 둘레", example = "10.5")
    @NotNull
    private double chestRound;

    @Schema(description = "이미지 S3 링크")
    private String imageURL;
}
