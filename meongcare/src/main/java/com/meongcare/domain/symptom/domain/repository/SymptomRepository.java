package com.meongcare.domain.symptom.domain.repository;

import com.meongcare.domain.symptom.domain.entity.Symptom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SymptomRepository extends JpaRepository<Symptom, Long> {

    default Symptom getById(Long id) {
        return this.findById(id)
                .orElseThrow(RuntimeException::new);
    }
}
