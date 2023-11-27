package com.meongcare.domain.supplements.domain.repository;

import com.meongcare.domain.supplements.domain.repository.vo.GetSupplementsRoutineVO;
import com.meongcare.domain.supplements.domain.repository.vo.QGetSupplementsRoutineVO;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

import static com.meongcare.domain.supplements.domain.entity.QSupplementsRecord.*;

@RequiredArgsConstructor
@Repository
public class SupplementsRecordQueryRepository {

    private final JPAQueryFactory queryFactory;

    public List<GetSupplementsRoutineVO> getByDogIdAndDate(Long dogId, LocalDate date) {
        return queryFactory
                .select(new QGetSupplementsRoutineVO(
                        supplementsRecord.id,
                        supplementsRecord.supplements.name,
                        supplementsRecord.supplementsTime.intakeCount,
                        supplementsRecord.supplements.intakeUnit,
                        supplementsRecord.supplementsTime.intakeTime,
                        supplementsRecord.intakeStatus
                ))
                .from(supplementsRecord)
                .where(
                        dogIdEq(dogId), dateEq(date), isActive(), isNotDeleted()
                )
                .fetch();
    }

    public int calSupplementsRate(Long dogId, LocalDate date) {
        Long totalRecordCount = queryFactory
                .select(supplementsRecord.count())
                .from(supplementsRecord)
                .where(dogIdEq(dogId), dateEq(date), isNotDeleted())
                .fetchOne();

        if (totalRecordCount == 0) {
            return 0;
        }

        Long intakeRecordCount = queryFactory
                .select(supplementsRecord.count())
                .from(supplementsRecord)
                .where(
                        dogIdEq(dogId), dateEq(date), isIntakeStatus(), isNotDeleted())
                .fetchOne();

        return Long.valueOf(intakeRecordCount * 100 / totalRecordCount).intValue();

    }
    private BooleanExpression dogIdEq(Long dogId) {
        return supplementsRecord.supplements.dog.id.eq(dogId);
    }

    private BooleanExpression dateEq(LocalDate date) {
        return supplementsRecord.date.eq(date);
    }

    private BooleanExpression isActive() {
        return supplementsRecord.supplements.isActive.isTrue();
    }

    private BooleanExpression isIntakeStatus() { return supplementsRecord.intakeStatus.isTrue();}

    private BooleanExpression isNotDeleted() { return supplementsRecord.supplements.deleted.isFalse();}

}
