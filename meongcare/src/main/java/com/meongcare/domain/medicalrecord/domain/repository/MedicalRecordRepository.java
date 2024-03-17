package com.meongcare.domain.medicalrecord.domain.repository;

import com.meongcare.common.error.ErrorCode;
import com.meongcare.common.error.exception.clientError.EntityNotFoundException;
import com.meongcare.domain.feed.domain.entity.Feed;
import com.meongcare.domain.medicalrecord.domain.entity.MedicalRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MedicalRecordRepository extends JpaRepository<MedicalRecord, Long> {
    default MedicalRecord getById(Long id) {
        return this.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.MEDICAL_RECORD_ENTITY_NOT_FOUND));
    }

    Optional<MedicalRecord> findByIdAndDeletedFalse(Long id);
}
