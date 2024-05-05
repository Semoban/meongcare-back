package com.meongcare.domain.dog.domain.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.meongcare.domain.dog.domain.entity.QMemberDog.memberDog;

@Repository
@RequiredArgsConstructor
public class MemberDogQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public List<Long> findDogIdsByMember(Long memberId) {
        return jpaQueryFactory
                .select(memberDog.dog.id)
                .from(memberDog)
                .where(memberIdEq(memberId), isNotDeleted())
                .fetch();
    }

    public void deleteByMember(Long memberId) {
        jpaQueryFactory
                .update(memberDog)
                .set(memberDog.deleted, true)
                .where(memberIdEq(memberId))
                .execute();
    }

    public void deleteByMemberAndDog(Long memberId, Long dogId) {
        jpaQueryFactory
                .update(memberDog)
                .set(memberDog.deleted, true)
                .where(memberIdEq(memberId), dogIdEq(dogId))
                .execute();
    }

    public static BooleanExpression dogIdEq(Long dogId) {
        return memberDog.dog.id.eq(dogId);
    }

    private static BooleanExpression memberIdEq(Long memberId) {
        return memberDog.member.id.eq(memberId);
    }

    private static BooleanExpression isNotDeleted() {
        return memberDog.deleted.isFalse();
    }
}
