package com.meongcare.domain.feed.domain.repository.vo;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class GetFeedRecordsVO {
    private String brandName;
    private String feedName;
    private LocalDate startDate;
    private LocalDate endDate;
    private Long feedRecordId;

    @QueryProjection
    public GetFeedRecordsVO(String brandName, String feedName, LocalDate startDate, LocalDate endDate, Long feedRecordId) {
        this.brandName = brandName;
        this.feedName = feedName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.feedRecordId = feedRecordId;
    }
}
