package com.meongcare.domain.feed.domain.entity;

import com.meongcare.common.BaseEntity;
import com.meongcare.domain.dog.domain.entity.Dog;
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
import java.time.LocalDateTime;

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

    private double kcal;

    private int recommendIntake;

    private String imageURL;

    private LocalDateTime dateTime;

    @JoinColumn(name = "dog_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Dog dog;

    @Builder
    public Feed(String brand, String feedName, double protein, double fat, double crudeAsh,
                double moisture, double kcal, int recommendIntake, String imageURL, LocalDateTime dateTime, Dog dog) {
        this.brand = brand;
        this.feedName = feedName;
        this.protein = protein;
        this.fat = fat;
        this.crudeAsh = crudeAsh;
        this.moisture = moisture;
        this.kcal = kcal;
        this.recommendIntake = recommendIntake;
        this.imageURL = imageURL;
        this.dateTime = dateTime;
        this.dog = dog;
    }
}
