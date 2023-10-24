package com.meongcare.auth.domain.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    private String name;

    private String email;

    private String provider;

    private String profileImage;

    private boolean pushAgreement;

    @Column(length = 500)
    private String fcmToken;

    @Column(length = 500)
    private String refreshToken;

    @Builder
    public Member(String name, String email, String provider, String profileImage, boolean pushAgreement, String fcmToken, String refreshToken) {
        this.name = name;
        this.email = email;
        this.provider = provider;
        this.profileImage = profileImage;
        this.pushAgreement = pushAgreement;
        this.fcmToken = fcmToken;
        this.refreshToken = refreshToken;
    }
}
