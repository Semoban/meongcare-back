package com.meongcare.domain.feed.presentation.dto.response;

import com.meongcare.domain.feed.domain.repository.vo.GetFeedDetailVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class GetFeedDetailResponse {

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

    @Schema(description = "칼로리", example = "230.34")
    private double kcal;

    @Schema(description = "권장 섭취량", example = "33")
    private int recommendIntake;

    @Schema(description = "사료 이미지 URL", example = "https://xxx.xxx.com")
    private String imageURL;

    @Schema(description = "사료 섭취 시작날짜", example = "2024-01-02")
    private LocalDate startDate;

    @Schema(description = "사료 섭취 종료날짜", example = "2024-01-04")
    private LocalDate endDate;

    public static GetFeedDetailResponse from(GetFeedDetailVO feedDetailVO) {
        return new GetFeedDetailResponse(
                feedDetailVO.getBrand(),
                feedDetailVO.getFeedName(),
                feedDetailVO.getProtein(),
                feedDetailVO.getFat(),
                feedDetailVO.getCrudeAsh(),
                feedDetailVO.getMoisture(),
                feedDetailVO.getKcal(),
                feedDetailVO.getRecommendIntake(),
                feedDetailVO.getImageURL(),
                feedDetailVO.getStartDate(),
                feedDetailVO.getEndDate()
        );
    }
}
