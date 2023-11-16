package com.meongcare.domain.feed.domain.repository;

import com.meongcare.domain.feed.domain.entity.Feed;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedRepository extends JpaRepository<Feed, Long> {

    default Feed getById(Long id) {
        return this.findById(id)
                .orElseThrow(RuntimeException::new);
    }
}
