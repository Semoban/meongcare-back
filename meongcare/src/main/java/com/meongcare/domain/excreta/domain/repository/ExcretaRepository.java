package com.meongcare.domain.excreta.domain.repository;

import com.meongcare.domain.excreta.domain.entity.Excreta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExcretaRepository extends JpaRepository<Excreta, Long> {

    default Excreta getById(Long id) {
        return this.findById(id)
                .orElseThrow(RuntimeException::new);
    }
}
