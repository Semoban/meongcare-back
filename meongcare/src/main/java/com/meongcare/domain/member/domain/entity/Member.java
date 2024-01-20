package com.meongcare.domain.member.domain.entity;

import com.meongcare.common.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;


@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseEntity {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @NotEmpty
    private String email;

    @Column(length = 2000, unique = true)
    @NotEmpty
    private String providerId;

    @Enumerated(EnumType.STRING)
    @NotNull
    private Provider provider;

    @NotEmpty
    private String profileImageUrl;

    @NotNull
    private boolean pushAgreement;

    @Column(length = 500)
    @NotEmpty
    private String fcmToken;


    @Builder
    public Member(Long id, String email, String providerId, Provider provider, String profileImageUrl, boolean pushAgreement, String fcmToken) {
        this.id = id;
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

    public void deleteMember() {
        delete();
    }

    public void updateProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }
}
