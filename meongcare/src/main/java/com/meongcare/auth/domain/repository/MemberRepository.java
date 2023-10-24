package com.meongcare.auth.domain.repository;

import com.meongcare.auth.domain.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
