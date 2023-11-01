package com.meongcare.domain.auth.presentation.dto.request;

import com.meongcare.domain.auth.domain.entity.Member;
import com.meongcare.domain.auth.domain.entity.Provider;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class LoginRequestDto {

    @NotNull
    private String providerId;

    @NotNull
    private String provider;

    @NotNull
    private String name;

    @NotNull
    @Email
    private String email;

    @NotNull
    private String profileImage;

    public Member toMemberEntity() {
        return Member
                .builder()
                .name(name)
                .provider(Provider.of(provider))
                .providerId(providerId)
                .pushAgreement(false)
                .profileImage(profileImage)
                .email(email)
                .fcmToken("example")
                .build();

    }


}
