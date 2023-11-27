package com.meongcare.domain.supplements.domain.repository;

import com.meongcare.domain.supplements.domain.entity.SupplementsRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SupplementsRecordRepository extends JpaRepository<SupplementsRecord, Long> {
    List<SupplementsRecord> findAllBySupplementsId(Long supplementsId);
}
