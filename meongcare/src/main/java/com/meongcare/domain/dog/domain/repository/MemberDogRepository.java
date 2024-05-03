package com.meongcare.domain.dog.domain.repository;

import com.meongcare.domain.dog.domain.entity.MemberDog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberDogRepository extends JpaRepository<MemberDog, Long> {
}
