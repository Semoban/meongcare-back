package com.meongcare.domain.feed.presentation.dto.response.vo;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class GetFeedRecordsPartVO {
    private String brandName;
    private String feedName;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String feedImageURL;

    @QueryProjection
    public GetFeedRecordsPartVO(String brandName, String feedName, LocalDateTime startDate, LocalDateTime endDate, String feedImageURL) {
        this.brandName = brandName;
        this.feedName = feedName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.feedImageURL = feedImageURL;
    }
}
