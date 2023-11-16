package com.meongcare.domain.feed.presentation.dto.response.vo;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

@Getter
public class GetFeedsVO {

    private Long feedId;
    private String brandName;
    private String feedName;

    @QueryProjection
    public GetFeedsVO(Long feedId, String brandName, String feedName) {
        this.feedId = feedId;
        this.brandName = brandName;
        this.feedName = feedName;
    }
}
