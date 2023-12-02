package com.meongcare.domain.feed.presentation.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class GetFeedRecommendIntakeForHomeResponse {

    @Schema(description = "사료 권장 섭취량", example = "30")
    private int recommendIntake;

    public static GetFeedRecommendIntakeForHomeResponse from(int recommendIntake) {
        return new GetFeedRecommendIntakeForHomeResponse(recommendIntake);
    }
}
