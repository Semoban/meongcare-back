package com.meongcare.domain.excreta.domain.repository;

import com.meongcare.domain.excreta.domain.entity.Excreta;
import com.meongcare.domain.excreta.domain.repository.vo.GetExcretaVO;
import com.meongcare.domain.excreta.domain.repository.vo.QGetExcretaVO;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

import static com.meongcare.domain.excreta.domain.entity.QExcreta.excreta;

@RequiredArgsConstructor
@Repository
public class ExcretaQueryRepository {

    private final JPAQueryFactory queryFactory;

    public List<GetExcretaVO> getByDogIdAndSelectedDate(
            Long dogId, LocalDateTime nowDateTime, LocalDateTime nextDateTime
    ) {
        return queryFactory
                .select(new QGetExcretaVO(
                        excreta.id,
                        excreta.dateTime,
                        excreta.type
                ))
                .from(excreta)
                .where(
                        dogIdEq(dogId),
                        dateTimeGoe(nowDateTime), dateTimeLt(nextDateTime),
                        isNotDeleted()
                )
                .fetch();
    }

    public List<Excreta> getByIds(List<Long> excretaIds) {
        return queryFactory
                .selectFrom(excreta)
                .where(
                        excreta.id.in(excretaIds),
                        isNotDeleted()
                )
                .fetch();
    }

    public void deleteExcreta(List<Long> excretaIds) {
        queryFactory
                .update(excreta)
                .set(excreta.deleted, true)
                .where(excreta.id.in(excretaIds))
                .execute();
    }

    private BooleanExpression isNotDeleted() {
        return excreta.deleted.isFalse();
    }

    private BooleanExpression dogIdEq(Long dogId) {
        return excreta.dog.id.eq(dogId);
    }

    private BooleanExpression dateTimeGoe(LocalDateTime nowDateTime) {
        return excreta.dateTime.goe(nowDateTime);
    }

    private BooleanExpression dateTimeLt(LocalDateTime nextDatetime) {
        return excreta.dateTime.lt(nextDatetime);
    }

}
