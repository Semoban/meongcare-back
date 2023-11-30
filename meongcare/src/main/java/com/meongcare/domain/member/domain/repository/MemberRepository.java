package com.meongcare.domain.member.domain.repository;

import com.meongcare.domain.member.domain.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByProviderId(String providerId);

    default Member getById(Long id) {
        return this.findById(id).orElseThrow(RuntimeException::new);
    }

}
