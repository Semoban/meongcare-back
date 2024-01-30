package com.meongcare.domain.feed.domain.repository;

import com.meongcare.common.error.ErrorCode;
import com.meongcare.common.error.exception.clientError.EntityNotFoundException;
import com.meongcare.domain.feed.domain.entity.FeedRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedRecordRepository extends JpaRepository<FeedRecord, Long> {
    default FeedRecord getById(Long id) {
        return this.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.FEED_RECORD_ENTITY_NOT_FOUND));
    }
}
