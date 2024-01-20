package com.meongcare.domain.feed.domain.repository.vo;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class GetFeedDetailVO {
    private String brand;
    private String feedName;
    private double protein;
    private double fat;
    private double crudeAsh;
    private double moisture;
    private double etc;
    private double kcal;
    private int recommendIntake;
    private String imageURL;
    private LocalDate startDate;
    private LocalDate endDate;

    @QueryProjection
    public GetFeedDetailVO(String brand, String feedName, double protein, double fat,
                           double crudeAsh, double moisture, double etc, double kcal,
                           int recommendIntake, String imageURL, LocalDate startDate, LocalDate endDate
    ) {
        this.brand = brand;
        this.feedName = feedName;
        this.protein = protein;
        this.fat = fat;
        this.crudeAsh = crudeAsh;
        this.moisture = moisture;
        this.etc = etc;
        this.kcal = kcal;
        this.recommendIntake = recommendIntake;
        this.imageURL = imageURL;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
