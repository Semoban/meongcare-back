package com.meongcare.domain.feed.presentation.dto.response;

import com.meongcare.common.util.LocalDateTimeUtils;
import com.meongcare.domain.feed.domain.entity.Feed;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.Objects;

@Builder
@AllArgsConstructor
@Getter
public class GetFeedResponse {

    @Schema(description = "브랜드명", example = "하림")
    private String brand;

    @Schema(description = "사료명", example = "더리얼 치킨 애견 사료")
    private String feedName;

    @Schema(description = "조단백", example = "13.2")
    private double protein;

    @Schema(description = "조지방", example = "15.1")
    private double fat;

    @Schema(description = "조회분", example = "3.3")
    private double crudeAsh;

    @Schema(description = "수분", example = "7.5")
    private double moisture;

    @Schema(description = "기타", example = "31.4")
    private double etc;

    @Schema(description = "섭취일수", example = "40")
    private long days;

    @Schema(description = "권장 섭취량", example = "3.3")
    private int recommendIntake;

    @Schema(description = "사료 ID", example = "3")
    private Long feedId;

    @Schema(description = "사료 섭취기록 ID", example = "1")
    private Long feedRecordId;

    public static GetFeedResponse of(LocalDate startDate, LocalDate endDate, Long feedRecordId, Feed feed) {
        long days = LocalDateTimeUtils.getBetweenDays(startDate, LocalDate.now());
        if (Objects.nonNull(endDate)) {
            days = LocalDateTimeUtils.getBetweenDays(startDate, endDate);
        }

        return GetFeedResponse.builder()
                .brand(feed.getBrand())
                .feedName(feed.getFeedName())
                .protein(feed.getProtein())
                .fat(feed.getFat())
                .crudeAsh(feed.getCrudeAsh())
                .moisture(feed.getMoisture())
                .etc(feed.getEtc())
                .days(days)
                .recommendIntake(feed.getRecommendIntake())
                .feedId(feed.getId())
                .feedRecordId(feedRecordId)
                .build();
    }

    public static GetFeedResponse empty() {
        return GetFeedResponse.builder()
                .build();
    }
}
