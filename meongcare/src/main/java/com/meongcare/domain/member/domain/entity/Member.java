package com.meongcare.domain.member.domain.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @NotNull
    private String name;

    @NotNull
    private String email;

    @NotNull
    private String providerId;

    @Enumerated(EnumType.STRING)
    @NotNull
    private Provider provider;

    @NotNull
    private String profileImageUrl;

    @NotNull
    private boolean pushAgreement;

    @Column(length = 500)
    @NotNull
    private String fcmToken;


    @Builder
    public Member(Long id, String name, String email, String providerId, Provider provider, String profileImageUrl, boolean pushAgreement, String fcmToken) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.providerId = providerId;
        this.provider = provider;
        this.profileImageUrl = profileImageUrl;
        this.pushAgreement = pushAgreement;
        this.fcmToken = fcmToken;
    }

    public void updatePushAgreement(boolean pushAgreement) {
        this.pushAgreement = pushAgreement;
    }

}
