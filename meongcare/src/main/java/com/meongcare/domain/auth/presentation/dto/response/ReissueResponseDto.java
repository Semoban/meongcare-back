package com.meongcare.domain.auth.presentation.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ReissueResponseDto {
    private String accessToken;

    @Builder
    public ReissueResponseDto(String accessToken) {
        this.accessToken = accessToken;
    }
}
