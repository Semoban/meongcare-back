package com.meongcare.domain.weight.application;

import com.meongcare.common.error.exception.clientError.EntityNotFoundException;
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
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static com.meongcare.common.util.LocalDateTimeUtils.createLastMonthFirstDayDate;
import static com.meongcare.common.util.LocalDateTimeUtils.createThisMonthLastDayDate;
import static com.meongcare.common.util.LocalDateTimeUtils.createThisWeekLastDay;
import static com.meongcare.common.util.LocalDateTimeUtils.createThreeWeeksAgoStartDay;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;

@Transactional
@SpringBootTest
class WeightServiceTest {

    @MockBean
    protected DogRepository dogRepository;

    @MockBean
    protected WeightQueryRepository weightQueryRepository;

    @MockBean
    protected WeightJdbcRepository weightJdbcRepository;

    @Autowired
    private WeightService weightService;

    private static final EasyRandom easyRandom = new EasyRandom();

    @Test
    @DisplayName("3주 전 일요일을 시작으로 이번 주의 마지막 날까지의 데이터를 호출한다.")
    void getWeekWeight() {
        //given
        Dog dog = easyRandom.nextObject(Dog.class);
        LocalDate now = LocalDate.now();

        LocalDate threeWeeksAgoStartDay = createThreeWeeksAgoStartDay(now);
        LocalDate thisWeekLastDay = createThisWeekLastDay(now);
        List<GetWeekWeightVO> getWeekWeightVOS = List.of(
                //3주전
                new GetWeekWeightVO(10, threeWeeksAgoStartDay),
                new GetWeekWeightVO(20, threeWeeksAgoStartDay.plusDays(1)),

                //2주전
                new GetWeekWeightVO(11, threeWeeksAgoStartDay.plusWeeks(1)),
                new GetWeekWeightVO(22, threeWeeksAgoStartDay.plusWeeks(1).plusDays(1)),

                //1주전
                new GetWeekWeightVO(12, threeWeeksAgoStartDay.plusWeeks(2)),
                new GetWeekWeightVO(24, threeWeeksAgoStartDay.plusWeeks(2).plusDays(1)),

                //이번주
                new GetWeekWeightVO(15, thisWeekLastDay),
                new GetWeekWeightVO(25, thisWeekLastDay.minusDays(1))
        );
        given(weightQueryRepository.getWeekWeightByDogIdAndDate(anyLong(), any(), any()))
                .willReturn(getWeekWeightVOS);
        //when
        GetWeekWeightResponse response = weightService.getWeekWeight(dog.getId(), now);

        //then
        List<GetWeekWeightResponse.Week> weeks = response.getWeeks();
        assertThat(weeks.get(0).getWeight()).isEqualTo(15);
        assertThat(weeks.get(1).getWeight()).isEqualTo(16.5);
        assertThat(weeks.get(2).getWeight()).isEqualTo(18);
        assertThat(weeks.get(3).getWeight()).isEqualTo(20);


    }

    @Test
    @DisplayName("지난달과 이번달의 체중 평균 데이터를 조회한다.")
    void getLastMonthAndThisMonthWeight() {

        //given
        Dog dog = easyRandom.nextObject(Dog.class);
        LocalDate now = LocalDate.now();
        LocalDate lastMonthFirstDayDate = createLastMonthFirstDayDate(now);
        LocalDate thisMonthLastDayDate = createThisMonthLastDayDate(now);
        List<GetMonthWeightVO> getMonthWeightVOS = List.of(
                //지난달 데이터
                new GetMonthWeightVO(10, lastMonthFirstDayDate.getMonthValue()),
                new GetMonthWeightVO(20, lastMonthFirstDayDate.getMonthValue()),

                //이번달 데이터
                new GetMonthWeightVO(30, thisMonthLastDayDate.getMonthValue()),
                new GetMonthWeightVO(40, thisMonthLastDayDate.getMonthValue())
        );

        given(weightQueryRepository.getMonthWeightByDogIdAndDate(anyLong(), any(), any()))
                .willReturn(getMonthWeightVOS);

        //when
        GetMonthWeightResponse response = weightService.getMonthWeight(dog.getId(), now);

        //then
        assertThat(response.getLastMonthWeight()).isEqualTo(30);
        assertThat(response.getThisMonthWeight()).isEqualTo(70);
    }

    @Test
    @DisplayName("강아지 체중을 수정할 수 있다.")
    void updateWeight() {
        //given
        Dog dog = easyRandom.nextObject(Dog.class);
        double originWeight = 15.0;
        Weight weight = Weight.createWeight(originWeight, dog);
        given(weightQueryRepository.getWeightByDogIdAndDate(anyLong(), any()))
                .willReturn(Optional.of(weight));

        double updateWeight = 16.0;
        //when
        weightService.updateWeight(dog.getId(), updateWeight, LocalDate.now());

        //then
        assertThat(weight.getKg()).isEqualTo(updateWeight);
    }

    @Test
    @DisplayName("강아지 체중 데이터 수정 시 체중 데이터가 없으면 예외를 발생 시킨다.")
    void updateWeightThrowExceptionWhenNoWeight() {
        //given
        Long dogId = 10L;
        double updateWeight = 5.0;
        LocalDate now = LocalDate.now();

        //when, then
        assertThatThrownBy(() -> weightService.updateWeight(dogId, updateWeight, now))
                .isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    @DisplayName("특정 날짜를 기준으로 강아지 몸무게 데이터를 저장할 수 있다.")
    void saveWeight() {
        //given
        Dog dog = easyRandom.nextObject(Dog.class);

        LocalDate now = LocalDate.now();
        LocalDate lastWeekFromNow = now.minusWeeks(1);
        GetLastDayWeightVO getLastDayWeightVO = new GetLastDayWeightVO(lastWeekFromNow, 20);

        given(weightQueryRepository.getRecentDayWeightByDogIdAndDate(anyLong(), any()))
                .willReturn(getLastDayWeightVO);


        //when
        int weightSaveSize = weightService.saveWeight(dog.getId(), now, null);

        //then
        assertThat(LocalDateTimeUtils.getBetweenDays(now, getLastDayWeightVO.getDate()))
                .isEqualTo(weightSaveSize);
    }

    @Test
    @DisplayName("선택한 날짜의 몸무게를 조회한다")
    void getDayWeight() {
        //given
        Dog dog = easyRandom.nextObject(Dog.class);
        LocalDate now = LocalDate.now();
        double weight = 15.0;

        given(weightQueryRepository.getDayWeightByDogIdAndDate(anyLong(), any()))
                .willReturn(Optional.of(weight));

        //when
        GetDayWeightResponse response = weightService.getDayWeight(dog.getId(), now);

        //then
        assertThat(response.getWeight()).isEqualTo(weight);
    }
}