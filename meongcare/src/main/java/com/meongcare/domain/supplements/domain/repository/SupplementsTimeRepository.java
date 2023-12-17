package com.meongcare.domain.supplements.domain.repository;

import com.meongcare.common.error.ErrorCode;
import com.meongcare.common.error.exception.EntityNotFoundException;
import com.meongcare.domain.supplements.domain.entity.SupplementsTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SupplementsTimeRepository extends JpaRepository<SupplementsTime, Long> {

    default SupplementsTime getById(Long id) {
        return this.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.SUPPLEMENTS_TIME_ENTITY_NOT_FOUND));
    }

    List<SupplementsTime> findAllBySupplementsId(Long supplementsId);
}
