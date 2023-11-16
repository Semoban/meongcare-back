package com.meongcare.domain.feed.presentation.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
public class ChangeFeedRequest {

    @Schema(description = "강아지 ID", example = "1")
    @NotNull
    private Long dogId;

    @Schema(description = "변경하려는 새로운 사료 ID", example = "1")
    @NotNull
    private Long newFeedId;
}
