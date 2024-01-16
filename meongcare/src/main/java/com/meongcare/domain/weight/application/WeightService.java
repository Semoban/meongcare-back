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
import com.meongcare.domain.weight.presentation.dto.response.GetDayWeightResponse;
import com.meongcare.domain.weight.presentation.dto.response.GetMonthWeightResponse;
import com.meongcare.domain.weight.presentation.dto.response.GetWeekWeightResponse;
import com.meongcare.domain.weight.presentation.dto.response.GetWeightForHomeResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
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
        List<GetWeekWeightVO> weekWeightVO = weightQueryRepository.getWeekWeightByDogIdAndDateTime(
                dogId,
                createThreeWeeksAgoStartDay(date),
                createThisWeekLastDay(date)
        );

        return GetWeekWeightResponse.of(weekWeightVO, date);
    }

    public GetMonthWeightResponse getMonthWeight(Long dogId, LocalDate date) {
        List<GetMonthWeightVO> weightVO = weightQueryRepository.getMonthWeightByDogIdAndDateTime(
                dogId,
                createLastMonthFirstDayDate(date),
                createThisMonthLastDayDate(date)
        );
        return GetMonthWeightResponse.of(weightVO, date);
    }

    @Transactional
    public void updateWeight(Long dogId, double kg, LocalDate date) {
        Weight weight = weightQueryRepository.getWeightByDogIdAndDate(dogId, date)
                .orElseThrow(IllegalArgumentException::new);

        weight.modifyWeight(kg);
    }

    @Transactional
    public void saveWeight(Long dogId, LocalDate date, Double kg) {
        GetLastDayWeightVO weightVO = weightQueryRepository.getRecentDayWeightByDogIdAndDateTime(
                dogId,
                date
        );

        long betweenDays = LocalDateTimeUtils.getBetweenDays(date, weightVO.getDate());

        List<Weight> weights = LongStream.range(0, betweenDays)
                .mapToObj(minusDays -> Weight.createBeforeWeight(
                        date, minusDays, weightVO.getKg(), dogId
                ))
                .collect(Collectors.toUnmodifiableList());

        if (Objects.nonNull(kg)) {
            weights.get(TODAY_WEIGHT).modifyWeight(kg);
        }
        weightJdbcRepository.saveWeight(weights);
    }

    public GetDayWeightResponse getDayWeight(Long dogId, LocalDate date) {
        Double weight = weightQueryRepository.getDayWeightByDogIdAndDateTime(
                dogId,
                date
        ).orElse(DEFAULT_WEIGHT);

        return GetDayWeightResponse.from(weight);
    }

    public GetWeightForHomeResponse getWeightForHome(Long dogId, LocalDate date) {
        Double weight = weightQueryRepository.getDayWeightByDogIdAndDateTime(
                dogId,
                date
        ).orElse(DEFAULT_WEIGHT);

        return GetWeightForHomeResponse.from(weight);
    }
}
