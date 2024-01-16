package com.meongcare.domain.feed.domain.repository.vo;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

@Getter
public class GetFeedsVO {

    private Long feedId;
    private String brandName;
    private String feedName;
    private String imageURL;

    @QueryProjection
    public GetFeedsVO(Long feedId, String brandName, String feedName, String imageURL) {
        this.feedId = feedId;
        this.brandName = brandName;
        this.feedName = feedName;
        this.imageURL = imageURL;
    }
}
