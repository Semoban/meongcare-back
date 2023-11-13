package com.meongcare.domain.notice.presentation.dto.response;

import com.meongcare.domain.notice.domain.repository.vo.GetNoticesVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Getter
public class GetNoticesResponse {
    List<Record> records;

    @AllArgsConstructor
    @Getter
    static class Record {
        @Schema(description = "공지사항 ID", example = "1")
        private Long noticeId;
        @Schema(description = "공지사항 제목", example = "[공지] 공지 제목")
        private String title;
        @Schema(description = "공지사항 본문", example = "공지사항 입니다.")
        private String text;
        @Schema(description = "공지 날짜/시간", example = "2023-11-13'T'17:00:23")
        private LocalDateTime lastUpdateTime;
    }

    public static GetNoticesResponse from(List<GetNoticesVO> noticesVO) {
        List<Record> records = noticesVO.stream()
                .map(notice -> new GetNoticesResponse.Record(
                        notice.getNoticeId(),
                        notice.getTitle(),
                        notice.getText(),
                        notice.getUpdateTime()
                ))
                .collect(Collectors.toUnmodifiableList());
        return new GetNoticesResponse(records);
    }
}
