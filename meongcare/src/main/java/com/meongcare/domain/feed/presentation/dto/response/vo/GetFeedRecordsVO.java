package com.meongcare.domain.feed.presentation.dto.response.vo;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class GetFeedRecordsVO {
    private String brandName;
    private String feedName;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    @QueryProjection
    public GetFeedRecordsVO(String brandName, String feedName, LocalDateTime startDate, LocalDateTime endDate) {
        this.brandName = brandName;
        this.feedName = feedName;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
