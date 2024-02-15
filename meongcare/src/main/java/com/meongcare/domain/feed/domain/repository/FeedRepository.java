package com.meongcare.domain.feed.domain.repository;

import com.meongcare.common.error.ErrorCode;
import com.meongcare.common.error.exception.clientError.EntityNotFoundException;
import com.meongcare.domain.feed.domain.entity.Feed;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FeedRepository extends JpaRepository<Feed, Long> {

    default Feed getById(Long id) {
        return this.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.FEED_ENTITY_NOT_FOUND));
    }

    Optional<Feed> findByIdAndDeletedFalse(Long id);
}
