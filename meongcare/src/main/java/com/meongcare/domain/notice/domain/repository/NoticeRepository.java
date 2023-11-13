package com.meongcare.domain.notice.domain.repository;

import com.meongcare.domain.notice.domain.entity.Notice;
import com.meongcare.domain.notice.domain.entity.NoticeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoticeRepository extends JpaRepository<Notice, Long> {

}
