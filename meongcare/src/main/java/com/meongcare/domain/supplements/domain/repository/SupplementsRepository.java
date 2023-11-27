package com.meongcare.domain.supplements.domain.repository;

import com.meongcare.domain.supplements.domain.entity.Supplements;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SupplementsRepository extends JpaRepository<Supplements, Long> {
    default Supplements getById(Long id){
        return this.findById(id)
                .orElseThrow(RuntimeException::new);
    }
}
