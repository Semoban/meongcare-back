package com.meongcare.domain.auth.domain.entity;

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

    private String providerId;

    @Enumerated(EnumType.STRING)
    private Provider provider;

    private String profileImage;

    private boolean pushAgreement;

    @Column(length = 500)
    private String fcmToken;


    @Builder
    public Member(Long id, String name, String email, String providerId, Provider provider, String profileImage, boolean pushAgreement, String fcmToken) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.providerId = providerId;
        this.provider = provider;
        this.profileImage = profileImage;
        this.pushAgreement = pushAgreement;
        this.fcmToken = fcmToken;
    }

}
