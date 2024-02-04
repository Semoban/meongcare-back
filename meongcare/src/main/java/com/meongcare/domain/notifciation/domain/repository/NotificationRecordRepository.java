package com.meongcare.domain.notifciation.domain.repository;

import com.meongcare.domain.notifciation.domain.entity.NotificationRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRecordRepository extends JpaRepository<NotificationRecord, Long> {

}
