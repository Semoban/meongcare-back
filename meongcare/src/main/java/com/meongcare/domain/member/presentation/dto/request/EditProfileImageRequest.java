package com.meongcare.domain.member.presentation.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class EditProfileImageRequest {

    @Schema(description = "이미지 링크")
    private String imageURL;
}
