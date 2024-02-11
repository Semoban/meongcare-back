package com.meongcare.domain.supplements.domain.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;


import java.util.List;

import static com.meongcare.domain.supplements.domain.entity.QSupplements.*;


@RequiredArgsConstructor
@Repository
public class SupplementsQueryRepository {

    private final JPAQueryFactory queryFactory;


    public List<Long> getSupplementsIdsByDogId(Long dogId) {
        return queryFactory
                .select(supplements.id)
                .from(supplements)
                .where(
                        dogIdEq(dogId)
                )
                .fetch();
    }

    private BooleanExpression dogIdEq(Long dogId) {
        return supplements.dog.id.eq(dogId);
    }
}
