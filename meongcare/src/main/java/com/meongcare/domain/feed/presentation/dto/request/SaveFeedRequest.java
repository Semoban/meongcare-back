package com.meongcare.domain.feed.presentation.dto.request;

import com.meongcare.domain.dog.domain.entity.Dog;
import com.meongcare.domain.feed.domain.entity.Feed;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
public class SaveFeedRequest {

    @Schema(description = "강아지 ID", example = "1")
    @NotNull
    private Long dogId;

    @Schema(description = "브랜드명", example = "알포")
    @NotNull
    private String brand;

    @Schema(description = "제품명", example = "강아지 사료")
    @NotNull
    private String feedName;

    @Schema(description = "조단백질", example = "3.12")
    private double protein;

    @Schema(description = "조지방", example = "8.23")
    private double fat;

    @Schema(description = "조회분", example = "2.22")
    private double crudeAsh;

    @Schema(description = "수분", example = "5.02")
    private double moisture;

    @Schema(description = "칼로리", example = "230.45")
    private double kcal;

    @Schema(description = "권장 섭취량", example = "35")
    private int recommendIntake;

    @Schema(description = "섭취 시작 날짜", example = "2023-10-13")
    @NotNull
    private LocalDate startDate;

    @Schema(description = "섭취 종료 날짜", example = "2023-12-14")
    private LocalDate endDate;

    public Feed toEntity(String imageURL, Dog dog, boolean isFirstRegisterFeed) {
        return Feed.builder()
                .brand(brand)
                .feedName(feedName)
                .protein(protein)
                .fat(fat)
                .crudeAsh(crudeAsh)
                .moisture(moisture)
                .kcal(kcal)
                .recommendIntake(recommendIntake)
                .imageURL(imageURL)
                .dog(dog)
                .isActivate(isFirstRegisterFeed)
                .build();
    }
}
