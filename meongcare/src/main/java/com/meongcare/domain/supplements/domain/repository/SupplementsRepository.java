package com.meongcare.domain.supplements.domain.repository;

import com.meongcare.domain.supplements.domain.entity.Supplements;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SupplementsRepository extends JpaRepository<Supplements, Long> {
    List<Supplements> findAllByDogId(Long dogId);
    Optional<Supplements> findByDogId(Long dogId);

    default Supplements getByDogId(Long dogId){
        return this.findByDogId(dogId)
                .orElseThrow(RuntimeException::new);
    }

}
