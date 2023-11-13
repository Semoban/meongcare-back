package com.meongcare.domain.notice.application;

import com.meongcare.domain.notice.domain.entity.Notice;
import com.meongcare.domain.notice.domain.entity.NoticeType;
import com.meongcare.domain.notice.domain.repository.NoticeQueryRepository;
import com.meongcare.domain.notice.domain.repository.NoticeRepository;
import com.meongcare.domain.notice.domain.repository.vo.GetNoticesVO;
import com.meongcare.domain.notice.presentation.dto.request.SaveNoticeRequest;
import com.meongcare.domain.notice.presentation.dto.response.GetNoticesResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class NoticeService {

    private final NoticeRepository noticeRepository;
    private final NoticeQueryRepository noticeQueryRepository;

    public void saveNotice(SaveNoticeRequest saveNoticeRequest) {
        Notice notice = saveNoticeRequest.toEntity();
        noticeRepository.save(notice);
    }

    public GetNoticesResponse getNotices(String noticeType) {
        List<GetNoticesVO> noticesVO = noticeQueryRepository.findByNoticeType(NoticeType.of(noticeType));

        return GetNoticesResponse.from(noticesVO);
    }
}
