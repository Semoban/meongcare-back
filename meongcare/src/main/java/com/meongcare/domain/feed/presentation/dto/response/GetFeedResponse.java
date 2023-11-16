package com.meongcare.domain.feed.presentation.dto.response;

import com.meongcare.domain.feed.domain.entity.Feed;
import com.meongcare.domain.feed.domain.entity.FeedRecord;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

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

    @Schema(description = "섭취일수", example = "40")
    private long days;

    @Schema(description = "권장 섭취량", example = "3.3")
    private int recommendIntake;

    @Schema(description = "사료 ID", example = "3")
    private Long feedId;

    public static GetFeedResponse of(long days, Feed feed) {
        return GetFeedResponse.builder()
                .brand(feed.getBrand())
                .feedName(feed.getFeedName())
                .protein(feed.getProtein())
                .fat(feed.getFat())
                .crudeAsh(feed.getCrudeAsh())
                .moisture(feed.getMoisture())
                .days(days)
                .recommendIntake(feed.getRecommendIntake())
                .feedId(feed.getId())
                .build();
    }
}
