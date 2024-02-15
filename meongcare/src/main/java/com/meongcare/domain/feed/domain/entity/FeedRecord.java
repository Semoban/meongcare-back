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
import java.time.LocalDate;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class FeedRecord extends BaseEntity {

    @Id
    @GeneratedValue
    private Long id;

    private LocalDate startDate;

    private LocalDate endDate;

    private Long dogId;

    private boolean isActive;

    @JoinColumn(name = "feed_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Feed feed;


    @Builder
    public FeedRecord(LocalDate startDate, LocalDate endDate, Long dogId, Feed feed, boolean isActive) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.dogId = dogId;
        this.feed = feed;
        this.isActive = isActive;
    }

    public static FeedRecord of(Feed feed, Long dogId, LocalDate startDate, LocalDate endDate, boolean isActive) {
        return FeedRecord.builder()
                .startDate(startDate)
                .endDate(endDate)
                .dogId(dogId)
                .feed(feed)
                .isActive(isActive)
                .build();
    }
    public void updateEndDate() {
        this.endDate = LocalDate.now();
    }

    public void updateDate(LocalDate startDate, LocalDate endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public void disActivate() {
        this.isActive = false;
    }
}
