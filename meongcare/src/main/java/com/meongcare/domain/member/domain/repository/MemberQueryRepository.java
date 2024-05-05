package com.meongcare.domain.member.domain.repository;

import com.meongcare.domain.member.domain.entity.Member;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.meongcare.domain.dog.domain.entity.QMemberDog.memberDog;
import static com.meongcare.domain.member.domain.entity.QMember.member;


@RequiredArgsConstructor
@Repository
public class MemberQueryRepository {

    private final JPAQueryFactory queryFactory;

    public boolean existMemberByEmail(String email) {
        return Optional.ofNullable(queryFactory
                .selectFrom(member)
                .where(
                        member.email.eq(email),
                        memberIsNotDeleted()
                )
                .fetchFirst()
        ).isPresent();

    }

    public Optional<Member> getMemberByEmail(String email) {
        return Optional.ofNullable(queryFactory
                .selectFrom(member)
                .where(
                        member.email.eq(email),
                        memberIsNotDeleted()
                )
                .fetchOne());

    }
    public List<Member> findAllByDog(Long dogId) {
        return queryFactory
                .select(member)
                .from(memberDog)
                .innerJoin(memberDog.member, member).fetchJoin()
                .where(dogIdEq(dogId), memberIsNotDeleted())
                .fetch();
    }


    private Predicate memberIsNotDeleted() {
        return member.deleted.isFalse();
    }

    public BooleanExpression dogIdEq(Long dogId) {
        return memberDog.dog.id.eq(dogId);
    }
}
