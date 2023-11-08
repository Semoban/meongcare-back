package com.meongcare.domain.weight.presentation.dto.response;

import com.meongcare.common.util.LocalDateTimeUtils;
import com.meongcare.domain.weight.domain.repository.vo.GetWeekWeightVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.meongcare.common.util.LocalDateTimeUtils.getPeriodDate;

@AllArgsConstructor
@Getter
public class GetWeekWeightResponse {

    private List<Week> weeks;

    @Getter
    @AllArgsConstructor
    static class Week {
        @Schema(description = "주차별 몸무게", example = "12.4")
        private double weight;
        @Schema(description = "주차별 기간", example = "11.08 ~ 11.14")
        private String periodDate;
    }

    public static GetWeekWeightResponse of(List<GetWeekWeightVO> weekWeightVO, LocalDateTime dateTime) {
        LocalDateTime threeWeeksAgoStartDay = LocalDateTimeUtils.createThreeWeeksAgoStartDay(dateTime);
        LocalDateTime threeWeeksAgoLastDay = LocalDateTimeUtils.createThisWeekLastDay(threeWeeksAgoStartDay);

        List<Week> weeks = IntStream.rangeClosed(0, 3)
                .mapToObj(increaseNumber -> {
                    LocalDateTime startDay = threeWeeksAgoStartDay.plusWeeks(increaseNumber);
                    LocalDateTime lastDay = threeWeeksAgoLastDay.plusWeeks(increaseNumber);
                    double weight = getWeightAverage(weekWeightVO, startDay, lastDay);
                    String dateRange = getPeriodDate(startDay, lastDay);
                    return new Week(weight, dateRange);
                })
                .collect(Collectors.toList());

        return new GetWeekWeightResponse(weeks);
    }

    private static double getWeightAverage(List<GetWeekWeightVO> weekWeightVO, LocalDateTime startDay, LocalDateTime lastDay) {
        return weekWeightVO.stream()
                .filter(vo -> vo.getDateTime().isAfter(startDay) && vo.getDateTime().isBefore(lastDay))
                .mapToDouble(GetWeekWeightVO::getWeight)
                .average()
                .orElse(0);
    }
}
