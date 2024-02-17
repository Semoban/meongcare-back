package com.meongcare.domain.notifciation.domain.repository;

import com.meongcare.domain.notifciation.domain.entity.NotificationRecord;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.meongcare.domain.notifciation.domain.entity.QNotificationRecord.notificationRecord;

@Repository
@RequiredArgsConstructor
public class NotificationRecordQueryRepository {

    private final JPAQueryFactory queryFactory;

    public List<NotificationRecord> findAll() {
        return queryFactory
                .selectFrom(notificationRecord)
                .fetch();
    }

    public void deleteByIds(List<Long> notificationRecordIds) {
        queryFactory
                .delete(notificationRecord)
                .where(notificationRecord.id.in(notificationRecordIds))
                .execute();
    }
}
