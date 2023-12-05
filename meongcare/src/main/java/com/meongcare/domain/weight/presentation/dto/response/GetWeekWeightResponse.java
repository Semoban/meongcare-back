package com.meongcare.domain.weight.presentation.dto.response;

import com.meongcare.common.util.LocalDateTimeUtils;
import com.meongcare.domain.weight.domain.repository.vo.GetWeekWeightVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class GetWeekWeightResponse {

    private List<Week> weeks;

    @Getter
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    static class Week {
        @Schema(description = "주차별 몸무게", example = "12.4")
        private double weight;

        @Schema(description = "주차 시작 날짜", example = "2023-11-08")
        private LocalDate startDate;

        @Schema(description = "주차 마지막 기간", example = "2023-11-14")
        private LocalDate endDate;
    }

    public static GetWeekWeightResponse of(List<GetWeekWeightVO> weekWeightVO, LocalDate date) {
        LocalDate threeWeeksAgoStartDayDate = LocalDateTimeUtils.createThreeWeeksAgoStartDay(date);
        LocalDate threeWeeksAgoLastDayDate = LocalDateTimeUtils.createThisWeekLastDay(threeWeeksAgoStartDayDate);

        List<Week> weeks = IntStream.rangeClosed(0, 3)
                .mapToObj(increaseNumber -> {
                    LocalDate startDay = threeWeeksAgoStartDayDate.plusWeeks(increaseNumber);
                    LocalDate lastDay = threeWeeksAgoLastDayDate.plusWeeks(increaseNumber);
                    double weight = getWeightAverage(weekWeightVO, startDay, lastDay);
                    return new Week(weight,startDay, lastDay);
                })
                .collect(Collectors.toList());

        return new GetWeekWeightResponse(weeks);
    }

    private static double getWeightAverage(List<GetWeekWeightVO> weekWeightVO, LocalDate startDay, LocalDate lastDay) {
        return weekWeightVO.stream()
                .filter(vo -> vo.getDate().isAfter(startDay) && vo.getDate().isBefore(lastDay))
                .mapToDouble(GetWeekWeightVO::getWeight)
                .average()
                .orElse(0);
    }
}
