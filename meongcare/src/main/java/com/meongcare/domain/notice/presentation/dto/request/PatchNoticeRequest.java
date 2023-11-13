package com.meongcare.domain.notice.presentation.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
public class PatchNoticeRequest {

    @Schema(description = "공지사항 고유 ID", example = "1")
    @NotNull
    private Long noticeId;

    @Schema(description = "수정할 공지사항 제목", example = "[공자] 수정된 공지 제목")
    @NotNull
    private String title;

    @Schema(description = "수정할 공지사항 내용", example = "수정된 공지사항 입니다.")
    @NotNull
    @Size(max = 2000)
    private String text;

}
