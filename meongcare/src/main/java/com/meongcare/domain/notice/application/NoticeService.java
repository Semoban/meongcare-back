package com.meongcare.domain.notice.application;

import com.meongcare.domain.notice.domain.entity.Notice;
import com.meongcare.domain.notice.domain.repository.NoticeRepository;
import com.meongcare.domain.notice.presentation.dto.request.SaveNoticeRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class NoticeService {

    private final NoticeRepository noticeRepository;

    public void saveNotice(SaveNoticeRequest saveNoticeRequest) {
        Notice notice = saveNoticeRequest.toEntity();
        noticeRepository.save(notice);
    }
}
