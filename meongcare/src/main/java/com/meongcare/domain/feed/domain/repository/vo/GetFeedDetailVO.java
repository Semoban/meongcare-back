package com.meongcare.domain.feed.domain.repository.vo;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

@Getter
public class GetFeedDetailVO {
    private String brand;
    private String feedName;
    private double protein;
    private double fat;
    private double crudeAsh;
    private double moisture;
    private double kcal;
    private int recommendIntake;
    private String imageURL;

    @QueryProjection
    public GetFeedDetailVO(String brand, String feedName, double protein, double fat, double crudeAsh, double moisture, double kcal, int recommendIntake, String imageURL) {
        this.brand = brand;
        this.feedName = feedName;
        this.protein = protein;
        this.fat = fat;
        this.crudeAsh = crudeAsh;
        this.moisture = moisture;
        this.kcal = kcal;
        this.recommendIntake = recommendIntake;
        this.imageURL = imageURL;
    }
}
