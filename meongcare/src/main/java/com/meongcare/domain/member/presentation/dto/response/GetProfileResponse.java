package com.meongcare.domain.member.presentation.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
public class GetProfileResponse {

    @Schema(description = "유저 이메일", example = "abc@naver.com")
    private String email;
    @Schema(description = "유저 프로필 이미지 링크", example = "https://meongcare-bucket/member/fedwf")
    private String imageUrl;
    @Schema(description = "유저 알림 설정 여부", example = "false")
    private boolean pushAgreement;

    @Builder
    public GetProfileResponse(String email, String imageUrl, boolean pushAgreement) {
        this.email = email;
        this.imageUrl = imageUrl;
        this.pushAgreement = pushAgreement;
    }

    public static GetProfileResponse of(String email, String imageUrl, boolean pushAgreement) {
        return GetProfileResponse
                .builder()
                .email(email)
                .imageUrl(imageUrl)
                .pushAgreement(pushAgreement)
                .build();

    }
}
