package com.meongcare.domain.weight.presentation.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class GetWeekWeightResponse {

    private List<Week> weeks;

    @Getter
    @AllArgsConstructor(access = AccessLevel.PUBLIC)
    public static class Week {
        @Schema(description = "주차별 몸무게", example = "12.4")
        private double weight;

        @Schema(description = "주차 시작 날짜", example = "2023-11-08")
        private LocalDate startDate;

        @Schema(description = "주차 마지막 기간", example = "2023-11-14")
        private LocalDate endDate;
    }

    public static GetWeekWeightResponse from(List<Week> weeks) {
        return new GetWeekWeightResponse(weeks);
    }
}
