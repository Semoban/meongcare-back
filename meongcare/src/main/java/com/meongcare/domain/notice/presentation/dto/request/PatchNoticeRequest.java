package com.meongcare.domain.notice.presentation.dto.request;

import lombok.Getter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
public class PatchNoticeRequest {

    @NotNull
    private Long noticeId;

    @NotNull
    private String title;

    @NotNull
    @Size(max = 2000)
    private String text;

}
