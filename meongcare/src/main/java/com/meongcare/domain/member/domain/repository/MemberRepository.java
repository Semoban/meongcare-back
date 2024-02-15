package com.meongcare.domain.member.domain.repository;

import com.meongcare.common.error.ErrorCode;
import com.meongcare.common.error.exception.clientError.EntityNotFoundException;
import com.meongcare.domain.member.domain.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByProviderId(String providerId);

    default Member getUser(Long id) {
        return this.findByIdAndDeleted(id, false)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.MEMBER_NOT_FOUND));
    }

    Optional<Member> findByIdAndDeleted(Long id, boolean deleted);

}
