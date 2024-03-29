package com.meongcare.domain.symptom.domain.repository;

import com.meongcare.domain.symptom.domain.repository.vo.GetSymptomVO;
import com.meongcare.domain.symptom.domain.repository.vo.QGetSymptomVO;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

import static com.meongcare.domain.symptom.domain.entity.QSymptom.symptom;

@RequiredArgsConstructor
@Repository
public class SymptomQueryRepository {
    private final JPAQueryFactory queryFactory;

    public List<GetSymptomVO> getSymptomByDogIdAndSelectedDate(
            Long dogId, LocalDateTime nowDateTime, LocalDateTime nextDateTime
    ) {
        return queryFactory
                .select(new QGetSymptomVO(
                        symptom.id,
                        symptom.dateTime,
                        symptom.symptomType,
                        symptom.note
                ))
                .from(symptom)
                .where(
                        dogIdEq(dogId),
                        dateTimeGoe(nowDateTime), dateTimeLt(nextDateTime),
                        isNotDeleted()
                )
                .fetch();
    }

    public void deleteSymptomById(List<Long> ids) {
        queryFactory
                .update(symptom)
                .set(symptom.deleted, true)
                .where(symptom.id.in(ids))
                .execute();
    }

    public void deleteSymptomDogId(Long dogId) {
        queryFactory
                .update(symptom)
                .set(symptom.deleted, true)
                .where(symptom.dog.id.eq(dogId))
                .execute();
    }

    private BooleanExpression isNotDeleted() {
        return symptom.deleted.isFalse();
    }

    private BooleanExpression dateTimeLt(LocalDateTime nextDateTime) {
        return symptom.dateTime.lt(nextDateTime);
    }

    private BooleanExpression dogIdEq(Long dogId) {
        return symptom.dog.id.eq(dogId);
    }

    private BooleanExpression dateTimeGoe(LocalDateTime nowDateTime) {
        return symptom.dateTime.goe(nowDateTime);
    }
}
