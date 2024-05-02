package com.meongcare.domain.weight.application;

import com.meongcare.common.error.ErrorCode;
import com.meongcare.common.error.exception.clientError.EntityNotFoundException;
import com.meongcare.common.util.LocalDateTimeUtils;
import com.meongcare.domain.dog.domain.repository.DogRepository;
import com.meongcare.domain.dog.domain.entity.Dog;
import com.meongcare.domain.weight.domain.entity.Weight;
import com.meongcare.domain.weight.domain.repository.WeightJdbcRepository;
import com.meongcare.domain.weight.domain.repository.WeightQueryRepository;
import com.meongcare.domain.weight.domain.repository.vo.GetLastDayWeightVO;
import com.meongcare.domain.weight.domain.repository.vo.GetMonthWeightVO;
import com.meongcare.domain.weight.domain.repository.vo.GetWeekWeightVO;
import com.meongcare.domain.weight.presentation.dto.response.GetDayWeightResponse;
import com.meongcare.domain.weight.presentation.dto.response.GetMonthWeightResponse;
import com.meongcare.domain.weight.presentation.dto.response.GetWeekWeightResponse;
import com.meongcare.domain.weight.presentation.dto.response.GetWeightForHomeResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

import static com.meongcare.common.util.LocalDateTimeUtils.createThreeWeeksAgoStartDay;
import static com.meongcare.common.util.LocalDateTimeUtils.createLastMonthFirstDayDate;
import static com.meongcare.common.util.LocalDateTimeUtils.createThisMonthLastDayDate;
import static com.meongcare.common.util.LocalDateTimeUtils.createThisWeekLastDay;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class WeightService {

    private final DogRepository dogRepository;
    private final WeightQueryRepository weightQueryRepository;
    private final WeightJdbcRepository weightJdbcRepository;

    private static final int TODAY_WEIGHT = 0;
    private static final Double DEFAULT_WEIGHT = 0.0;

    public GetWeekWeightResponse getWeekWeight(Long dogId, LocalDate date) {
        List<GetWeekWeightVO> weekWeightVO = weightQueryRepository.getWeekWeightByDogIdAndDate(
                dogId,
                createThreeWeeksAgoStartDay(date),
                createThisWeekLastDay(date)
        );
        LocalDate threeWeeksAgoStartDayDate = LocalDateTimeUtils.createThreeWeeksAgoStartDay(date);
        LocalDate threeWeeksAgoLastDayDate = LocalDateTimeUtils.createThisWeekLastDay(threeWeeksAgoStartDayDate);

        List<GetWeekWeightResponse.Week> weeks = IntStream.rangeClosed(0, 3)
                .mapToObj(increaseWeek -> {
                    LocalDate startDay = threeWeeksAgoStartDayDate.plusWeeks(increaseWeek);
                    LocalDate lastDay = threeWeeksAgoLastDayDate.plusWeeks(increaseWeek);
                    double weight = getWeightAverage(weekWeightVO, startDay, lastDay);
                    return new GetWeekWeightResponse.Week(weight,startDay, lastDay);
                })
                .collect(Collectors.toList());

        return GetWeekWeightResponse.from(weeks);
    }

    private double getWeightAverage(List<GetWeekWeightVO> weekWeightVO, LocalDate startDay, LocalDate lastDay) {
        return weekWeightVO.stream()
                .filter(vo -> (vo.getDate().isAfter(startDay) && vo.getDate().isBefore(lastDay))
                        || vo.getDate().isEqual(startDay) || vo.getDate().isEqual(lastDay))
                .mapToDouble(GetWeekWeightVO::getWeight)
                .average()
                .orElse(DEFAULT_WEIGHT);
    }

    public GetMonthWeightResponse getMonthWeight(Long dogId, LocalDate date) {
        List<GetMonthWeightVO> weightVO = weightQueryRepository.getMonthWeightByDogIdAndDate(
                dogId,
                createLastMonthFirstDayDate(date),
                createThisMonthLastDayDate(date)
        );

        Map<Integer, Double> monthWeightMap = weightVO.stream()
                .collect(Collectors.groupingBy(GetMonthWeightVO::getMonth,
                        Collectors.summingDouble(GetMonthWeightVO::getWeight)));

        double lastMonthWeight = monthWeightMap.getOrDefault(date.minusMonths(1).getMonthValue(), 0.0);;
        double thisMonthWeight = monthWeightMap.getOrDefault(date.getMonthValue(), 0.0);;

        return GetMonthWeightResponse.of(lastMonthWeight, thisMonthWeight);
    }

    @Transactional
    public void updateWeight(Long dogId, double kg, LocalDate date) {
        Weight weight = weightQueryRepository.getWeightByDogIdAndDate(dogId, date)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.WEIGHT_ENTITY_NOT_FOUND));

        weight.modifyWeight(kg);
    }

    @Transactional
    public int saveWeight(Long dogId, LocalDate date, Double kg) {
        Dog dog = dogRepository.getDog(dogId);
        GetLastDayWeightVO weightVO = weightQueryRepository.getRecentDayWeightByDogIdAndDate(
                dogId,
                date
        );

        long betweenDays = LocalDateTimeUtils.getBetweenDays(date, weightVO.getDate());

        List<Weight> weights = LongStream.range(0, betweenDays)
                .mapToObj(minusDays -> Weight.createBeforeWeight(
                        date, minusDays, weightVO.getKg(), dog
                ))
                .collect(Collectors.toUnmodifiableList());

        if (Objects.nonNull(kg)) {
            weights.get(TODAY_WEIGHT).modifyWeight(kg);
        }
        weightJdbcRepository.saveWeight(weights);
        return weights.size();
    }

    public GetDayWeightResponse getDayWeight(Long dogId, LocalDate date) {
        Double weight = weightQueryRepository.getDayWeightByDogIdAndDate(
                dogId,
                date
        ).orElse(DEFAULT_WEIGHT);

        return GetDayWeightResponse.from(weight);
    }

    public GetWeightForHomeResponse getWeightForHome(Long dogId, LocalDate date) {
        Double weight = weightQueryRepository.getDayWeightByDogIdAndDate(
                dogId,
                date
        ).orElse(DEFAULT_WEIGHT);

        return GetWeightForHomeResponse.from(weight);
    }
}
