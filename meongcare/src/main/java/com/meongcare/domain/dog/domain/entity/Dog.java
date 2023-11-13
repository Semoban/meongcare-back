package com.meongcare.domain.dog.domain.entity;

import com.meongcare.domain.member.domain.entity.Member;
import com.meongcare.domain.dog.presentation.dto.request.PutDogRequest;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Dog {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @JoinColumn(name = "member_id")
    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull
    private Member member;

    @NotNull
    private String name;

    @NotNull
    private String type;

    @NotNull
    private String imageUrl;

    @Enumerated(EnumType.STRING)
    @NotNull
    private Sex sex;

    @NotNull
    private boolean castrate;

    private LocalDateTime birthDate;

    @NotNull
    private double neckRound;

    @NotNull
    private double chestRound;

    private double weight;

    @Builder
    public Dog(Member member, String name, String type, String imageUrl, Sex sex,
               boolean castrate, LocalDateTime birthDate, double neckRound, double chestRound, double weight) {
        this.member = member;
        this.name = name;
        this.type = type;
        this.imageUrl = imageUrl;
        this.sex = sex;
        this.castrate = castrate;
        this.birthDate = birthDate;
        this.neckRound = neckRound;
        this.chestRound = chestRound;
        this.weight = weight;
    }

    public void updateWeight(double weight) {
        this.weight = weight;
    }

    public void updateAll(PutDogRequest putDogRequest, String dogImageURL) {
        this.name = putDogRequest.getName();
        this.type = putDogRequest.getType();
        this.imageUrl = dogImageURL;
        this.sex = Sex.of(putDogRequest.getSex());
        this.castrate = putDogRequest.isCastrate();
        this.birthDate = putDogRequest.getBirthDate();
        this.neckRound = putDogRequest.getNeckRound();
        this.chestRound = putDogRequest.getChestRound();
        this.weight = putDogRequest.getWeight();
    }
}
