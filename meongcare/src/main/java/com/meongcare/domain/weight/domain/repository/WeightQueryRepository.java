package com.meongcare.domain.weight.domain.repository;

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

import java.time.LocalDateTime;
import java.util.List;

import static com.meongcare.domain.weight.domain.entity.QWeight.weight;

@RequiredArgsConstructor
@Repository
public class WeightQueryRepository {

    private final JPAQueryFactory queryFactory;

    public List<GetMonthWeightVO> getMonthWeightByDogIdAndDateTime(
            Long dogId,
            LocalDateTime beforeMonthDateTime,
            LocalDateTime nowMonthDateTime
    ) {
        return queryFactory
                .select(new QGetMonthWeightVO(
                        weight.kg.avg(),
                        weight.dateTime.month()
                ))
                .from(weight)
                .where(
                        dogIdEq(dogId),
                        dateTimeGoe(beforeMonthDateTime), dateTimeLoe(nowMonthDateTime)
                )
                .groupBy(weight.dateTime.month())
                .fetch();
    }

    public List<GetWeekWeightVO> getWeekWeightByDogIdAndDateTime(
            Long dogId,
            LocalDateTime beforeDateTime,
            LocalDateTime afterDateTime
    ) {
        return queryFactory
                .select(new QGetWeekWeightVO(
                        weight.kg,
                        weight.dateTime
                ))
                .from(weight)
                .where(
                        dogIdEq(dogId),
                        dateTimeGoe(beforeDateTime), dateTimeLoe(afterDateTime)
                )
                .fetch();
    }

    public GetLastDayWeightVO getRecentDayWeightByDogIdAndDateTime(Long dogId, LocalDateTime dateTime) {
        return queryFactory
                .select(new QGetLastDayWeightVO(
                        weight.dateTime,
                        weight.kg
                ))
                .from(weight)
                .where(dogIdEq(dogId),
                        dateTimeLt(dateTime)
                )
                .orderBy(weight.dateTime.desc())
                .fetchFirst();
    }

    public double getDayWeightByDogIdAndDateTime(Long dogId, LocalDateTime nowMidnight, LocalDateTime nextMidnight) {
        return queryFactory
                .select(weight.kg)
                .from(weight)
                .where(
                        dogIdEq(dogId),
                        dateTimeGoe(nowMidnight), dateTimeLt(nextMidnight)
                )
                .fetchFirst();
    }

    private BooleanExpression dateTimeLt(LocalDateTime dateTime) {
        return weight.dateTime.lt(dateTime);
    }

    private BooleanExpression dogIdEq(Long dogId) {
        return weight.dogId.eq(dogId);
    }

    private BooleanExpression dateTimeLoe(LocalDateTime dateTime) {
        return weight.dateTime.loe(dateTime);
    }

    private BooleanExpression dateTimeGoe(LocalDateTime dateTime) {
        return weight.dateTime.goe(dateTime);
    }
}
