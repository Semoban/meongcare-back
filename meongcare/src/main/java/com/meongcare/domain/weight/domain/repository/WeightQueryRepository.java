package com.meongcare.domain.weight.domain.repository;

import com.meongcare.domain.weight.domain.entity.Weight;
import com.meongcare.domain.weight.domain.repository.vo.GetLastDayWeightVO;
import com.meongcare.domain.weight.domain.repository.vo.GetMonthWeightVO;
import com.meongcare.domain.weight.domain.repository.vo.GetWeekWeightVO;
import com.meongcare.domain.weight.domain.repository.vo.QGetLastDayWeightVO;
import com.meongcare.domain.weight.domain.repository.vo.QGetMonthWeightVO;
import com.meongcare.domain.weight.domain.repository.vo.QGetWeekWeightVO;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static com.meongcare.domain.weight.domain.entity.QWeight.weight;

@RequiredArgsConstructor
@Repository
public class WeightQueryRepository {

    private final JPAQueryFactory queryFactory;

    public List<GetMonthWeightVO> getMonthWeightByDogIdAndDate(
            Long dogId,
            LocalDate lastMonthFirstDayDate,
            LocalDate thisMonthLastDayDate
    ) {
        return queryFactory
                .select(new QGetMonthWeightVO(
                        weight.kg.avg(),
                        weight.date.month()
                ))
                .from(weight)
                .where(
                        dogIdEq(dogId),
                        dateGoe(lastMonthFirstDayDate), dateLoe(thisMonthLastDayDate)
                )
                .groupBy(weight.date.month())
                .fetch();
    }

    public List<GetWeekWeightVO> getWeekWeightByDogIdAndDate(
            Long dogId,
            LocalDate threeWeeksAgoStartDayDate,
            LocalDate thisWeekLastDayDate
    ) {
        return queryFactory
                .select(new QGetWeekWeightVO(
                        weight.kg,
                        weight.date
                ))
                .from(weight)
                .where(
                        dogIdEq(dogId),
                        dateGoe(threeWeeksAgoStartDayDate), dateLoe(thisWeekLastDayDate)
                )
                .fetch();
    }

    public GetLastDayWeightVO getRecentDayWeightByDogIdAndDate(Long dogId, LocalDate date) {
        return queryFactory
                .select(new QGetLastDayWeightVO(
                        weight.date,
                        weight.kg
                ))
                .from(weight)
                .where(dogIdEq(dogId),
                        dateLoe(date)
                )
                .orderBy(weight.date.desc())
                .fetchFirst();
    }

    public Optional<Double> getDayWeightByDogIdAndDate(Long dogId, LocalDate date) {
        return Optional.ofNullable(queryFactory
                .select(weight.kg)
                .from(weight)
                .where(
                        dogIdEq(dogId),
                        dateEq(date)
                )
                .fetchOne());
    }

    public Optional<Weight> getWeightByDogIdAndDate(Long dogId, LocalDate date) {
        return Optional.ofNullable(queryFactory
                .selectFrom(weight)
                .where(
                        dogIdEq(dogId),
                        dateEq(date)
                )
                .fetchOne());
    }

    public boolean existRequestDateIsGoeLastWeightDate(Long dogId, LocalDate date) {
        Weight fetchWeight = queryFactory
                .selectFrom(weight)
                .where(
                        dogIdEq(dogId),
                        dateGoe(date)
                )
                .fetchFirst();
        return fetchWeight != null;
    }

    public void deleteWeightByDogId(Long dogId) {
        queryFactory
                .update(weight)
                .set(weight.deleted, true)
                .where(dogIdEq(dogId))
                .execute();
    }

    private BooleanExpression dateEq(LocalDate date) {
        return weight.date.eq(date);
    }

    private BooleanExpression dateLt(LocalDate date) {
        return weight.date.lt(date);
    }

    private BooleanExpression dogIdEq(Long dogId) {
        return weight.dog.id.eq(dogId);
    }

    private BooleanExpression dateLoe(LocalDate date) {
        return weight.date.loe(date);
    }

    private BooleanExpression dateGoe(LocalDate date) {
        return weight.date.goe(date);
    }
}
