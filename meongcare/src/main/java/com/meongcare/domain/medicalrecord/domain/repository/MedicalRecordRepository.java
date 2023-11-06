package com.meongcare.domain.medicalrecord.domain.repository;

import com.meongcare.domain.medicalrecord.domain.entity.MedicalRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicalRecordRepository extends JpaRepository<MedicalRecord, Long> {
}
