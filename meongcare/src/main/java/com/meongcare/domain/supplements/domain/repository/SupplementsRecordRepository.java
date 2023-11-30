package com.meongcare.domain.supplements.domain.repository;

import com.meongcare.domain.supplements.domain.entity.SupplementsRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface SupplementsRecordRepository extends JpaRepository<SupplementsRecord, Long> {
    default SupplementsRecord getById(Long id) {
        return this.findById(id)
                .orElseThrow(RuntimeException::new);
    }
}
