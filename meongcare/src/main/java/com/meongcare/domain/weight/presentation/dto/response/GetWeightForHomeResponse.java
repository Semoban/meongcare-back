package com.meongcare.domain.weight.presentation.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class GetWeightForHomeResponse {

    @Schema(description = "몸무게", example = "5.6")
    private Double weight;

    public static GetWeightForHomeResponse from(Double weight) {
        return new GetWeightForHomeResponse(weight);
    }
}
