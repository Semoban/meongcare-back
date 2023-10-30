package com.meongcare.auth.domain.repository;

import com.meongcare.auth.domain.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    public Optional<Member> findByEmail(String email);

    public Optional<Member> findByProviderId(String providerId);
}
