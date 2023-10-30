package com.meongcare.dog.domain.entity;

import com.meongcare.auth.domain.entity.Member;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

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

    private String sex;

    private boolean castrate;

    private Date birthDate;

    private Date adoptDate;

    private double neckRound;

    private double chestRound;

    @Builder
    public Dog(Member member, String name, String type, String sex, boolean castrate, Date birthDate, Date adoptDate, double neckRound, double chestRound) {
        this.member = member;
        this.name = name;
        this.type = type;
        this.sex = sex;
        this.castrate = castrate;
        this.birthDate = birthDate;
        this.adoptDate = adoptDate;
        this.neckRound = neckRound;
        this.chestRound = chestRound;
    }
}
