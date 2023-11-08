package com.meongcare.domain.weight.application;

import com.meongcare.domain.dog.domain.DogRepository;
import com.meongcare.domain.dog.domain.entity.Dog;
import com.meongcare.domain.weight.domain.repository.WeightQueryRepository;
import com.meongcare.domain.weight.domain.repository.vo.GetMonthWeightVO;
import com.meongcare.domain.weight.domain.repository.vo.GetWeekWeightVO;
import com.meongcare.domain.weight.presentation.dto.response.GetMonthWeightResponse;
import com.meongcare.domain.weight.presentation.dto.response.GetWeekWeightResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

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
}
