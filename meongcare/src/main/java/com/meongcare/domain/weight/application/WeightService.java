package com.meongcare.domain.weight.application;

import com.meongcare.common.util.LocalDateTimeUtils;
import com.meongcare.domain.dog.domain.DogRepository;
import com.meongcare.domain.dog.domain.entity.Dog;
import com.meongcare.domain.weight.domain.entity.Weight;
import com.meongcare.domain.weight.domain.repository.WeightJdbcRepository;
import com.meongcare.domain.weight.domain.repository.WeightQueryRepository;
import com.meongcare.domain.weight.domain.repository.vo.GetLastDayWeightVO;
import com.meongcare.domain.weight.domain.repository.vo.GetMonthWeightVO;
import com.meongcare.domain.weight.domain.repository.vo.GetWeekWeightVO;
import com.meongcare.domain.weight.presentation.dto.response.GetMonthWeightResponse;
import com.meongcare.domain.weight.presentation.dto.response.GetWeekWeightResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

import static com.meongcare.common.util.LocalDateTimeUtils.createNextMidnight;
import static com.meongcare.common.util.LocalDateTimeUtils.createNowMidnight;
import static com.meongcare.common.util.LocalDateTimeUtils.createThreeWeeksAgoStartDay;
import static com.meongcare.common.util.LocalDateTimeUtils.createLastMonthDateTime;
import static com.meongcare.common.util.LocalDateTimeUtils.createThisMonthDateTime;
import static com.meongcare.common.util.LocalDateTimeUtils.createThisWeekLastDay;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class WeightService {

    private final DogRepository dogRepository;
    private final WeightQueryRepository weightQueryRepository;
    private final WeightJdbcRepository weightJdbcRepository;

    private static final int TODAY_WEIGHT = 0;

    public GetWeekWeightResponse getWeekWeight(Long dogId, LocalDateTime dateTime) {
        List<GetWeekWeightVO> weekWeightVO = weightQueryRepository.getWeekWeightByDogIdAndDateTime(
                dogId,
                createThreeWeeksAgoStartDay(dateTime),
                createThisWeekLastDay(dateTime)
        );

        return GetWeekWeightResponse.of(weekWeightVO, dateTime);
    }

    public GetMonthWeightResponse getMonthWeight(Long dogId, LocalDateTime dateTime) {
        List<GetMonthWeightVO> weightVO = weightQueryRepository.getMonthWeightByDogIdAndDateTime(
                dogId,
                createLastMonthDateTime(dateTime),
                createThisMonthDateTime(dateTime)
        );
        return GetMonthWeightResponse.of(weightVO, dateTime);
    }

    @Transactional
    public void updateWeight(Long dogId, double weight) {
        Dog dog = dogRepository.getById(dogId);
        dog.updateWeight(weight);
    }

    @Transactional
    public void saveWeight(Long dogId, LocalDateTime dateTime, Double kg) {
        GetLastDayWeightVO weightVO = weightQueryRepository.getRecentDayWeightByDogIdAndDateTime(
                dogId,
                dateTime
        );

        long betweenDays = LocalDateTimeUtils.getBetweenDays(dateTime, weightVO.getDateTime());

        List<Weight> weights = LongStream.range(0, betweenDays)
                .mapToObj(minusDays -> Weight.createBeforeWeight(
                        dateTime, minusDays, weightVO.getKg(), dogId
                ))
                .collect(Collectors.toUnmodifiableList());

        if (Objects.nonNull(kg)) {
            weights.get(TODAY_WEIGHT).modifyTodayWeight(kg);
        }
        weightJdbcRepository.saveWeight(weights);
        Dog dog = dogRepository.getById(dogId);
        dog.updateWeight(kg);
    }

    public double getDayWeight(Long dogId, LocalDateTime dateTime) {
        return weightQueryRepository.getDayWeightByDogIdAndDateTime(
                dogId,
                createNowMidnight(dateTime),
                createNextMidnight(dateTime)
        );
    }
}
