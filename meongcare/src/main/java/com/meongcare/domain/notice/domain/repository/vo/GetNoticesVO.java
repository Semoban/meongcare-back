package com.meongcare.domain.notice.domain.repository.vo;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class GetNoticesVO {
    private Long noticeId;
    private String title;
    private String text;
    private LocalDateTime updateTime;

    @QueryProjection
    public GetNoticesVO(Long noticeId, String title, String text, LocalDateTime updateTime) {
        this.noticeId = noticeId;
        this.title = title;
        this.text = text;
        this.updateTime = updateTime;
    }
}
