package com.meongcare.domain.member.presentation.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
public class GetProfileResponse {

    @Schema(description = "유저 이메일")
    private String email;
    @Schema(description = "유저 프로필 이미지 링크")
    private String imageUrl;

    @Builder
    public GetProfileResponse(String email, String imageUrl) {
        this.email = email;
        this.imageUrl = imageUrl;
    }

    public static GetProfileResponse of(String email, String imageUrl) {
        return GetProfileResponse
                .builder()
                .email(email)
                .imageUrl(imageUrl)
                .build();

    }
}
