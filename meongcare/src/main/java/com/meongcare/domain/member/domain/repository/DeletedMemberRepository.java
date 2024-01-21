package com.meongcare.domain.member.domain.repository;

import com.meongcare.domain.member.domain.entity.DeletedMember;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeletedMemberRepository extends JpaRepository<DeletedMember, Long> {
}
