package com.meongcare.domain.auth.presentation.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
public class LoginResponse {

    @Schema(description = "엑세스 토큰")
    private String accessToken;
    @Schema(description = "리프레시 토큰")
    private String refreshToken;
    @Schema(description = "최초 로그인 여부", example = "false")
    private Boolean isFirstLogin;

    @Builder
    public LoginResponse(String accessToken, String refreshToken, Boolean isFirstLogin) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.isFirstLogin = isFirstLogin;
    }

    public static LoginResponse of(String accessToken, String refreshToken, Boolean isFirstLogin) {
        return LoginResponse
                .builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .isFirstLogin(isFirstLogin)
                .build();

    }
}
