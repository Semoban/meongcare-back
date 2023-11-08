package com.meongcare.domain.weight.presentation.dto.response;

import com.meongcare.common.util.LocalDateTimeUtils;
import com.meongcare.domain.weight.domain.repository.vo.GetWeekWeightVO;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.meongcare.common.util.LocalDateTimeUtils.getPeriodDate;

@AllArgsConstructor
@Getter
public class GetWeightWeekResponse {

    private List<Week> weeks;

    @Getter
    @AllArgsConstructor
    static class Week {
        private double weight;
        private String periodDate;
    }

    public static GetWeightWeekResponse of(List<GetWeekWeightVO> weekWeightVO, LocalDateTime dateTime) {
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

        return new GetWeightWeekResponse(weeks);
    }

    private static double getWeightAverage(List<GetWeekWeightVO> weekWeightVO, LocalDateTime startDay, LocalDateTime lastDay) {
        return weekWeightVO.stream()
                .filter(vo -> vo.getDateTime().isAfter(startDay) && vo.getDateTime().isBefore(lastDay))
                .mapToDouble(GetWeekWeightVO::getWeight)
                .average()
                .orElse(0);
    }
}
