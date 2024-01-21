package com.meongcare.domain.member.domain.repository;

import com.meongcare.domain.member.domain.entity.RevokeMember;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RevokeMemberRepository extends JpaRepository<RevokeMember, Long> {
}
