package com.meongcare.domain.dog.domain;

import com.meongcare.domain.dog.domain.entity.DogWeight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DogWeightRepository extends JpaRepository<DogWeight, Long> {
}
