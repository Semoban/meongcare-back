package com.meongcare.domain.feed.presentation.dto.response;

import com.meongcare.domain.feed.domain.repository.vo.GetFeedRecordsPartVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
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

        @Schema(description = "먹기 시작한 날짜", example = "2023-10-24")
        private LocalDate startDate;

        @Schema(description = "그만 먹은 날짜", example = "2024-03-22")
        private LocalDate endDate;

        @Schema(description = "사료 이미지", example = "https://www.s3.com")
        private String feedImageURL;

        @Schema(description = "사료 섭취일정 ID", example = "1")
        private Long feedRecordId;
    }

    public static GetFeedsPartResponse from(List<GetFeedRecordsPartVO> feedRecords) {
        List<FeedPartRecord> feedPartRecords = feedRecords.stream()
                .map(vo -> new FeedPartRecord(
                        vo.getBrandName(),
                        vo.getFeedName(),
                        vo.getStartDate(),
                        vo.getEndDate(),
                        vo.getFeedImageURL(),
                        vo.getFeedRecordId()
                ))
                .collect(Collectors.toUnmodifiableList());
        return new GetFeedsPartResponse(feedPartRecords);
    }

}
