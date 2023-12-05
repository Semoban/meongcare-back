package com.meongcare.domain.weight.presentation.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class GetDayWeightResponse {

    @Schema(description = "당일 몸무게", example = "2.5")
    private double weight;

    public static GetDayWeightResponse from(double weight) {
        return new GetDayWeightResponse(weight);
    }
}
