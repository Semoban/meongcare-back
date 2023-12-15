package com.meongcare.domain.feed.domain.repository.vo;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class GetFeedRecordsPartVO {
    private String brandName;
    private String feedName;
    private LocalDate startDate;
    private LocalDate endDate;
    private String feedImageURL;
    private Long feedRecordId;

    @QueryProjection
    public GetFeedRecordsPartVO(String brandName, String feedName, LocalDate startDate, LocalDate endDate, String feedImageURL, Long feedRecordId) {
        this.brandName = brandName;
        this.feedName = feedName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.feedImageURL = feedImageURL;
        this.feedRecordId = feedRecordId;
    }
}
