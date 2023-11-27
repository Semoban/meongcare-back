package com.meongcare.domain.supplements.presentation.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class GetSupplementsRateResponse {

    @Schema(description = "영양제 섭취 완료율", example = "10")
    private int supplementsRate;

    public static GetSupplementsRateResponse from(int supplementsRate) {
        return new GetSupplementsRateResponse(supplementsRate);
    }
}
