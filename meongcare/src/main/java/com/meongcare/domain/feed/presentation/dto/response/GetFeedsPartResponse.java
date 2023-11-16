package com.meongcare.domain.feed.presentation.dto.response;

import com.meongcare.domain.feed.presentation.dto.response.vo.GetFeedRecordsPartVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

import static com.meongcare.common.util.LocalDateTimeUtils.getPeriodDateWithYearFormat;

@AllArgsConstructor
@Getter
public class GetFeedsPartResponse {

    private List<FeedPartRecord> feedPartRecords;

    @Getter
    @AllArgsConstructor
    static class FeedPartRecord {
        @Schema(description = "브랜드명", example = "하림")
        private String brandName;

        @Schema(description = "사료명", example = "더리얼 치킨 애견 사료")
        private String feedName;

        @Schema(description = "섭취 기간", example = "2022년 10월 16일 ~ 10월 23일")
        private String periodDate;

        @Schema(description = "사료 이미지", example = "https://www.s3.com")
        private String feedImageURL;
    }

    public static GetFeedsPartResponse from(List<GetFeedRecordsPartVO> feedRecords) {
        List<FeedPartRecord> feedPartRecords = feedRecords.stream()
                .map(vo -> new FeedPartRecord(
                        vo.getBrandName(),
                        vo.getFeedName(),
                        getPeriodDateWithYearFormat(vo.getStartDate(), vo.getEndDate()),
                        vo.getFeedImageURL()
                ))
                .collect(Collectors.toUnmodifiableList());
        return new GetFeedsPartResponse(feedPartRecords);
    }

}
