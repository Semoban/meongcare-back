package com.meongcare.domain.member.domain.repository;

import com.meongcare.domain.member.domain.entity.Member;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

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

    private Predicate memberIsNotDeleted() {
        return member.deleted.isFalse();
    }
}
