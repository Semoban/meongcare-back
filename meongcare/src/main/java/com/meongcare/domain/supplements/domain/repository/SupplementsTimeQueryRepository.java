package com.meongcare.domain.supplements.domain.repository;

import com.meongcare.domain.supplements.domain.repository.vo.GetSupplementsAndTimeVO;
import com.meongcare.domain.supplements.domain.repository.vo.GetSupplementsRoutineWithoutStatusVO;
import com.meongcare.domain.supplements.domain.repository.vo.QGetSupplementsAndTimeVO;
import com.meongcare.domain.supplements.domain.repository.vo.QGetSupplementsRoutineWithoutStatusVO;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalTime;
import java.util.List;

import static com.meongcare.domain.supplements.domain.entity.QSupplements.supplements;
import static com.meongcare.domain.supplements.domain.entity.QSupplementsTime.supplementsTime;

@RequiredArgsConstructor
@Repository
public class SupplementsTimeQueryRepository {

    private final JPAQueryFactory queryFactory;

    public List<GetSupplementsAndTimeVO> findAll() {
        return queryFactory
                .select(new QGetSupplementsAndTimeVO(
                        supplements,
                        supplementsTime
                ))
                .from(supplementsTime)
                .innerJoin(supplementsTime.supplements, supplements)
                .fetch();
    }

    public List<GetSupplementsRoutineWithoutStatusVO> findBySupplementsId(Long supplementsId) {
        return queryFactory
                .select(new QGetSupplementsRoutineWithoutStatusVO(
                        supplementsTime.supplements.name,
                        supplementsTime.intakeCount,
                        supplementsTime.supplements.intakeUnit,
                        supplementsTime.intakeTime
                ))
                .from(supplementsTime)
                .where(supplementsIdEq(supplementsId))
                .fetch();
    }

    private BooleanExpression supplementsIdEq(Long supplementsId) {
        return supplementsTime.supplements.id.eq(supplementsId);
    }
}
