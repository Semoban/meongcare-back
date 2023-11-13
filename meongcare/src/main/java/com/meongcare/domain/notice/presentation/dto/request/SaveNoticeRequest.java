package com.meongcare.domain.notice.presentation.dto.request;


import com.meongcare.domain.notice.domain.entity.Notice;
import com.meongcare.domain.notice.domain.entity.NoticeType;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
public class SaveNoticeRequest {
    @NotNull
    private String title;

    @NotNull
    @Size(max = 2000)
    private String text;

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
