package com.meongcare.domain.member.domain.entity;

import com.meongcare.common.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Objects;


@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseEntity {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    private String email;

    @Column(length = 2000, unique = true)
    private String providerId;

    @Enumerated(EnumType.STRING)
    private Provider provider;

    private String profileImageUrl;

    private boolean pushAgreement;

    @Column(length = 500)
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

    public void updateProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    public void deleteMember() {
        this.email = null;
        this.providerId = null;
        this.provider = null;
        this.profileImageUrl = null;
        this.pushAgreement = false;
        this.fcmToken = null;
        this.softDelete();
    }

    public boolean isNewFcmToken(String fcmToken) {
        return !Objects.equals(this.getFcmToken(), fcmToken);
    }

    public void updateFcmToken(String fcmToken) {
        this.fcmToken = fcmToken;
    }
}
