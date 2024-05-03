package com.meongcare.domain.dog.presentation.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ShareDogRequest {

    @Schema(description = "공유할 계정 이메일", example = "meongcare@gmail.com")
    @NotEmpty
    private String shareEmail;
}
