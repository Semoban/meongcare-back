package com.meongcare.domain.weight.domain.repository;

import com.meongcare.domain.weight.domain.repository.vo.GetMonthWeightVO;
import com.meongcare.domain.weight.domain.repository.vo.GetWeekWeightVO;
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

    private BooleanExpression dogIdEq(Long dogId) {
        return weight.dogId.eq(dogId);
    }

    private BooleanExpression dateTimeLoe(LocalDateTime nowMonthDateTime) {
        return weight.dateTime.loe(nowMonthDateTime);
    }

    private BooleanExpression dateTimeGoe(LocalDateTime beforeMonthDateTime) {
        return weight.dateTime.goe(beforeMonthDateTime);
    }
}