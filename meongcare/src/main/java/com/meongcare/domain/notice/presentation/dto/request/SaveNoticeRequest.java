package com.meongcare.domain.notice.presentation.dto.request;


import com.meongcare.domain.notice.domain.entity.Notice;
import com.meongcare.domain.notice.domain.entity.NoticeType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
public class SaveNoticeRequest {

    @Schema(description = "공지사항 제목", example = "[공자] 공지 제목")
    @NotNull
    private String title;

    @Schema(description = "공지사항 내용", example = "공지사항 입니다.")
    @NotNull
    @Size(max = 2000)
    private String text;

    @Schema(description = "공지사항 타입 (event/notice)", example = "event")
    @NotNull
    private String type;

    public Notice toEntity() {
        return Notice.builder()
                .title(title)
                .text(text)
                .noticeType(NoticeType.of(type))
                .build();
    }
}
