package com.meongcare.dog.domain;

import com.meongcare.dog.domain.entity.Dog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DogRepository extends JpaRepository<Dog, Long> {
}
