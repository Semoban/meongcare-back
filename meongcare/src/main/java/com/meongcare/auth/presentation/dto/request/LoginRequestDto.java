package com.meongcare.auth.presentation.dto.request;

import com.meongcare.auth.domain.entity.Member;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class LoginRequestDto {

    private String providerId;
    private String provider;
    private String name;
    private String email;
    private String profileImage;

    public Member toMemberEntity() {
        return Member
                .builder()
                .name(name)
                .provider(provider)
                .providerId(providerId)
                .pushAgreement(false)
                .profileImage(profileImage)
                .email(email)
                .fcmToken("example")
                .build();

    }


}
