package com.meongcare.domain.auth.presentation.dto.request;

import com.meongcare.domain.member.domain.entity.Member;
import com.meongcare.domain.member.domain.entity.Provider;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import static com.meongcare.domain.auth.application.AuthService.PROVIDER_ID_SEPARATOR;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class LoginRequest {

    @Schema(description = "소셜 로그인 고유 ID")
    @NotEmpty
    private String providerId;

    @Schema(description = "소셜 로그인 제공자(소문자)", example = "google")
    @NotEmpty
    private String provider;

    @Schema(description = "회원 이메일", example = "meongcare@gmail.com")
    @NotEmpty
    @Email
    private String email;

    @Schema(description = "프로필 이미지 URL", example = "http://meongcare.com/123")
    @NotNull
    private String profileImageUrl;

    @Schema(description = "fcm 토큰", example = "12askjdb123nmn~~~")
    @NotEmpty
    private String fcmToken;

    public Member toMemberEntity() {
        return Member
                .builder()
                .provider(Provider.of(provider))
                .providerId(providerId + PROVIDER_ID_SEPARATOR + provider)
                .pushAgreement(false)
                .profileImageUrl(profileImageUrl)
                .email(email)
                .fcmToken(fcmToken)
                .build();
    }
}
