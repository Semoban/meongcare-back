package com.meongcare.domain.feed.domain.entity;

import com.meongcare.common.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class FeedRecord extends BaseEntity {

    @Id
    @GeneratedValue
    private Long id;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private Long dogId;

    @JoinColumn(name = "feed_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Feed feed;


    @Builder
    public FeedRecord(LocalDateTime startDate, LocalDateTime endDate, Long dogId, Feed feed) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.dogId = dogId;
        this.feed = feed;
    }

    public static FeedRecord of(Feed feed, Long dogId) {
        return FeedRecord.builder()
                .startDate(LocalDateTime.now())
                .dogId(dogId)
                .feed(feed)
                .build();
    }

    public void updateEndDate() {
        this.endDate = LocalDateTime.now();
    }
}
