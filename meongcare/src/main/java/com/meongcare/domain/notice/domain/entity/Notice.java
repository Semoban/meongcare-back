package com.meongcare.domain.notice.domain.entity;

import com.meongcare.common.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Notice extends BaseEntity {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @NotNull
    private String title;

    @NotNull
    @Column(length = 2000)
    private String text;

    @NotNull
    @Enumerated(EnumType.STRING)
    private NoticeType noticeType;

    @Builder
    public Notice(String title, String text, NoticeType noticeType) {
        this.title = title;
        this.text = text;
        this.noticeType = noticeType;
    }

}
