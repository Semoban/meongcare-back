package com.meongcare.domain.auth.presentation.dto.request;

import com.meongcare.domain.auth.domain.entity.Member;
import lombok.*;

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
