package com.meongcare.domain.dog.domain.entity;

import com.meongcare.domain.auth.domain.entity.Member;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
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
    private Member member;

    private String name;

    private String type;

    private String profileImage;

    private String sex;

    private boolean castrate;

    private LocalDateTime birthDate;

    private double neckRound;

    private double chestRound;

    @Builder
    public Dog(Member member, String name, String type, String profileImage, String sex, boolean castrate, LocalDateTime birthDate, double neckRound, double chestRound) {
        this.member = member;
        this.name = name;
        this.type = type;
        this.profileImage = profileImage;
        this.sex = sex;
        this.castrate = castrate;
        this.birthDate = birthDate;
        this.neckRound = neckRound;
        this.chestRound = chestRound;
    }
}
