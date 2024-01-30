package com.meongcare.domain.excreta.domain.repository;

import com.meongcare.common.error.ErrorCode;
import com.meongcare.common.error.exception.clientError.EntityNotFoundException;
import com.meongcare.domain.excreta.domain.entity.Excreta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExcretaRepository extends JpaRepository<Excreta, Long> {

    default Excreta getById(Long id) {
        return this.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.EXCRETA_ENTITY_NOT_FOUND));
    }
}
