package com.meongcare.domain.member.domain.repository;

import com.meongcare.domain.member.domain.entity.RevokeMember;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface RevokeMemberRepository extends JpaRepository<RevokeMember, Long> {

    List<RevokeMember> findByRevokeDateBefore(LocalDateTime beforeTime);

    boolean existsByProviderId(String providerId);
}
