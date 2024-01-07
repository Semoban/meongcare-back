package com.meongcare.domain.feed.presentation.dto.response;

import com.meongcare.domain.feed.domain.repository.vo.GetFeedsVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Getter
public class GetFeedsResponse {

    private List<Feed> feeds;

    @AllArgsConstructor
    @Getter
    static class Feed {
        @Schema(description = "사료 ID", example = "1")
        private Long feedId;

        @Schema(description = "브랜드명", example = "하림")
        private String brandName;

        @Schema(description = "사료명", example = "사료명")
        private String feedName;

        @Schema(description = "사료 이미지 URL", example = "https://xxx.xxx.com")
        private String imageURL;
    }

    public static GetFeedsResponse from(List<GetFeedsVO> feedsVO) {
        List<Feed> feeds = feedsVO.stream()
                .map(vo -> new Feed(
                        vo.getFeedId(),
                        vo.getBrandName(),
                        vo.getFeedName(),
                        vo.getImageURL()
                ))
                .collect(Collectors.toUnmodifiableList());
        return new GetFeedsResponse(feeds);
    }
}
