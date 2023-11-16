package com.meongcare.domain.feed.domain.repository;

import com.meongcare.domain.feed.domain.entity.FeedRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedRecordRepository extends JpaRepository<FeedRecord, Long> {

}
