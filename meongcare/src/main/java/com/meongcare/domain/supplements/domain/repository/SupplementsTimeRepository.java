package com.meongcare.domain.supplements.domain.repository;

import com.meongcare.domain.supplements.domain.entity.SupplementsTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SupplementsTimeRepository extends JpaRepository<SupplementsTime, Long> {

    default SupplementsTime getById(Long id){
        return this.findById(id)
                .orElseThrow(RuntimeException::new);
    }
}
