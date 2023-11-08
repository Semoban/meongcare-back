package com.meongcare.domain.auth.presentation.dto.request;

import com.meongcare.domain.auth.domain.entity.Member;
import com.meongcare.domain.auth.domain.entity.Provider;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class LoginRequestDto {

    @Schema(description = "소셜 로그인 고유 ID")
    @NotNull
    private String providerId;

    @Schema(description = "소셜 로그인 제공자(소문자)", example = "google")
    @NotNull
    private String provider;

    @Schema(description = "회원 이름", example = "김땡땡")
    @NotNull
    private String name;

    @Schema(description = "회원 이메일", example = "meongcare@gmail.com")
    @NotNull
    @Email
    private String email;

    @Schema(description = "프로필 이미지 URL", example = "http://meongcare.com/123")
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
