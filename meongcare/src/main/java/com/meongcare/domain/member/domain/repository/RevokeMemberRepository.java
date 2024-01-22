package com.meongcare.domain.member.domain.repository;

import com.meongcare.domain.member.domain.entity.RevokeMember;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface RevokeMemberRepository extends JpaRepository<RevokeMember, Long> {

    List<RevokeMember> findByRevokeDateBefore(LocalDateTime beforeTime);

    Optional<RevokeMember> findByProviderId(String providerId);
}
