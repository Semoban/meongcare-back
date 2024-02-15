package com.meongcare.domain.notice.domain.repository;

import com.meongcare.common.error.ErrorCode;
import com.meongcare.common.error.exception.clientError.EntityNotFoundException;
import com.meongcare.domain.notice.domain.entity.Notice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoticeRepository extends JpaRepository<Notice, Long> {

    default Notice getById(Long noticeId){
        return this.findById(noticeId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.NOTICE_ENTITY_NOT_FOUND));
    }

}
