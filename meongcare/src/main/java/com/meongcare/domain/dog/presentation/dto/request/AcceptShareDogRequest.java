package com.meongcare.domain.dog.presentation.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AcceptShareDogRequest {

    @Schema(description = "공유 요청자 이메일", example = "meongcare@gmail.com")
    @NotEmpty
    private String requesterEmail;
}
