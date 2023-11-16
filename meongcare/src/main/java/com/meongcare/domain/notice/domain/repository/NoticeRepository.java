package com.meongcare.domain.notice.domain.repository;

import com.meongcare.domain.notice.domain.entity.Notice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoticeRepository extends JpaRepository<Notice, Long> {

    default Notice getById(Long noticeId){
        return this.findById(noticeId)
                .orElseThrow(RuntimeException::new);
    }

}
