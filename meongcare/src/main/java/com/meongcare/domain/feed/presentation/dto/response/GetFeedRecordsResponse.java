package com.meongcare.domain.feed.presentation.dto.response;

import com.meongcare.domain.feed.presentation.dto.response.vo.GetFeedRecordsVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

import static com.meongcare.common.util.LocalDateTimeUtils.getPeriodDateWithYearFormat;

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

        @Schema(description = "기간", example = "2023년 10월 24일 ~ 2024년 1월 12일")
        private String period;
    }

    public static GetFeedRecordsResponse from(List<GetFeedRecordsVO> getFeedRecordsVO) {
        List<FeedRecord> feedRecords = getFeedRecordsVO.stream()
                .map(vo -> new FeedRecord(
                        vo.getBrandName(),
                        vo.getFeedName(),
                        getPeriodDateWithYearFormat(vo.getStartDate(), vo.getEndDate())
                ))
                .collect(Collectors.toUnmodifiableList());
        return new GetFeedRecordsResponse(feedRecords);
    }
}
