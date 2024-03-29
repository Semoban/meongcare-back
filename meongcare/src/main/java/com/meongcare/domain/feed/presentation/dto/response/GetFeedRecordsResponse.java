package com.meongcare.domain.feed.presentation.dto.response;

import com.meongcare.domain.feed.domain.repository.vo.GetFeedRecordsVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Getter
public class GetFeedRecordsResponse {

    List<FeedRecord> feedRecords;

    @AllArgsConstructor
    @Getter
    static class FeedRecord {
        @Schema(description = "브랜드명", example = "하림")
        private String brand;

        @Schema(description = "사료명", example = "사료의 정석")
        private String feedName;

        @Schema(description = "먹기 시작한 날짜", example = "2023-10-24")
        private LocalDate startDate;

        @Schema(description = "그만 먹은 날짜", example = "2024-03-22")
        private LocalDate endDate;

        @Schema(description = "섭취 기록 ID", example = "1")
        private Long feedRecordId;

        @Schema(description = "사료 이미지 URL", example = "https://xxx.xxx.com")
        private String imageURL;

        @Schema(description = "사료 ID", example = "1")
        private Long feedId;
    }

    public static GetFeedRecordsResponse from(List<GetFeedRecordsVO> getFeedRecordsVO) {
        List<FeedRecord> feedRecords = getFeedRecordsVO.stream()
                .map(vo -> new FeedRecord(
                        vo.getBrandName(),
                        vo.getFeedName(),
                        vo.getStartDate(),
                        vo.getEndDate(),
                        vo.getFeedRecordId(),
                        vo.getImageURL(),
                        vo.getFeedId()
                ))
                .collect(Collectors.toUnmodifiableList());
        return new GetFeedRecordsResponse(feedRecords);
    }
}
