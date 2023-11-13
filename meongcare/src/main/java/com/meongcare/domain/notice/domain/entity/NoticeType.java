package com.meongcare.domain.notice.domain.entity;

import com.meongcare.domain.member.domain.entity.Provider;
import lombok.Getter;

import java.util.stream.Stream;

@Getter
public enum NoticeType {
    EVENT("event"),
    NOTICE("notice");

    private final String noticeType;

    NoticeType(String noticeType) {
        this.noticeType = noticeType;
    }

    public static NoticeType of(String noticeType) {
        return Stream.of(NoticeType.values())
                .filter(p -> p.getNoticeType().equals(noticeType))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
