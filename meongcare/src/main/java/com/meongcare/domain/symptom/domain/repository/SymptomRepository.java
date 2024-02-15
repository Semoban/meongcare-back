package com.meongcare.domain.symptom.domain.repository;

import com.meongcare.common.error.ErrorCode;
import com.meongcare.common.error.exception.clientError.EntityNotFoundException;
import com.meongcare.domain.symptom.domain.entity.Symptom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SymptomRepository extends JpaRepository<Symptom, Long> {

    default Symptom getById(Long id) {
        return this.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.SYMPTOM_ENTITY_NOT_FOUND));
    }

    Optional<Symptom> findByIdAndDeletedFalse(Long id);
}
