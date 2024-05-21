package com.meongcare.domain.dog.domain.entity;

import com.meongcare.common.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Dog extends BaseEntity {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @NotNull
    private String name;

    @NotNull
    private String type;

    private String imageUrl;

    @Enumerated(EnumType.STRING)
    @NotNull
    private Sex sex;

    @NotNull
    private boolean castrate;

    private LocalDate birthDate;

    @NotNull
    private double backRound;

    @NotNull
    private double neckRound;

    @NotNull
    private double chestRound;

    private double weight;

    @Builder
    public Dog(String name, String type, String imageUrl, Sex sex,
               boolean castrate, LocalDate birthDate, double backRound, double neckRound, double chestRound, double weight) {
        this.name = name;
        this.type = type;
        this.imageUrl = imageUrl;
        this.sex = sex;
        this.castrate = castrate;
        this.birthDate = birthDate;
        this.backRound = backRound;
        this.neckRound = neckRound;
        this.chestRound = chestRound;
        this.weight = weight;
    }

    public void updateWeight(double weight) {
        this.weight = weight;
    }

    public void updateAll(
            String name, String type, String imageUrl, Sex sex, boolean castrate, LocalDate birthDate,
            double backRound, double neckRound, double chestRound, double weight
    ) {
        this.name = name;
        this.type = type;
        this.imageUrl = imageUrl;
        this.sex = sex;
        this.castrate = castrate;
        this.birthDate = birthDate;
        this.backRound = backRound;
        this.neckRound = neckRound;
        this.chestRound = chestRound;
        this.weight = weight;
    }
}
