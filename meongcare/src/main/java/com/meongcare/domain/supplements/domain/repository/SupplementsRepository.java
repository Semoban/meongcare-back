package com.meongcare.domain.supplements.domain.repository;

import com.meongcare.common.error.ErrorCode;
import com.meongcare.common.error.exception.EntityNotFoundException;
import com.meongcare.domain.supplements.domain.entity.Supplements;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SupplementsRepository extends JpaRepository<Supplements, Long> {
    List<Supplements> findAllByDogId(Long dogId);

    List<Supplements> findAllByDogIdAndDeletedFalse(Long dogId);
    default Supplements getById(Long dogId){
        return this.findById(dogId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.SUPPLEMENTS_ENTITY_NOT_FOUND));
    }

}
