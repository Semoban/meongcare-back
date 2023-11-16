package com.meongcare.domain.dog.presentation.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
public class GetDogsResponse {

    @Schema(description = "강아지 고유 번호")
    private Long dogId;
    @Schema(description = "강아지 이름")
    private String name;
    @Schema(description = "강아지 imageUrl")
    private String imageUrl;

    @Builder
    public GetDogsResponse(Long dogId, String name, String imageUrl) {
        this.dogId = dogId;
        this.name = name;
        this.imageUrl = imageUrl;
    }

    public static GetDogsResponse of(Long dogId, String name, String imageUrl) {
        return GetDogsResponse
                .builder()
                .dogId(dogId)
                .name(name)
                .imageUrl(imageUrl)
                .build();
    }
}
