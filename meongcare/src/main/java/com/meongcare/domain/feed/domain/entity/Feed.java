package com.meongcare.domain.feed.domain.entity;

import com.meongcare.common.BaseEntity;
import com.meongcare.domain.dog.domain.entity.Dog;
import com.meongcare.domain.feed.presentation.dto.request.EditFeedRequest;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Feed extends BaseEntity {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

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

    private boolean isActivate;

    @JoinColumn(name = "dog_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Dog dog;

    @Builder
    public Feed(String brand, String feedName, double protein, double fat, double crudeAsh,
                double moisture, double etc, double kcal, int recommendIntake, String imageURL, boolean isActivate, Dog dog) {
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
        this.isActivate = isActivate;
        this.dog = dog;
    }

    public void disActivate() {
        this.isActivate = false;
    }

    public void activate() {
        this.isActivate = true;
    }

    public void updateInfo(EditFeedRequest request, String imageURL) {
        this.brand = request.getBrand();
        this.feedName = request.getFeedName();
        this.protein = request.getProtein();
        this.fat = request.getFat();
        this.crudeAsh = request.getCrudeAsh();
        this.moisture = request.getMoisture();
        this.etc = request.getEtc();
        this.kcal = request.getKcal();
        this.recommendIntake = request.getRecommendIntake();
        this.imageURL = imageURL;
    }
}
