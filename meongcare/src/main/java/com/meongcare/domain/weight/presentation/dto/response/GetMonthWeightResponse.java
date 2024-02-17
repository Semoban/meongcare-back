package com.meongcare.domain.weight.presentation.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class GetMonthWeightResponse {

    @Schema(description = "지난달 몸무게", example = "35.2")
    private double lastMonthWeight;
    @Schema(description = "이번달 몸무게", example = "36.3")
    private double thisMonthWeight;

    public static GetMonthWeightResponse of(double lastMonthWeight, double thisMonthWeight) {
        return new GetMonthWeightResponse(lastMonthWeight, thisMonthWeight);
    }
}
