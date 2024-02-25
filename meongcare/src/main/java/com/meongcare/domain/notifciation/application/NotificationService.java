package com.meongcare.domain.notifciation.application;

import com.meongcare.domain.member.domain.entity.Member;
import com.meongcare.domain.member.domain.repository.MemberRepository;
import com.meongcare.domain.notifciation.domain.dto.FcmNotificationDTO;
import com.meongcare.domain.notifciation.domain.entity.NotificationRecord;
import com.meongcare.domain.notifciation.domain.repository.NotificationRecordQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NotificationService {

    private final NotificationRecordQueryRepository notificationRecordQueryRepository;
    private final MemberRepository memberRepository;
    private final ApplicationEventPublisher eventPublisher;

    @Transactional
    public void retryNotification() {
        List<NotificationRecord> notificationRecords = notificationRecordQueryRepository.findAll();
        if (notificationRecords.isEmpty()) {
            return;
        }
        List<Long> memberIds = notificationRecords.stream()
                .map(NotificationRecord::getMemberId)
                .collect(Collectors.toList());

        Map<Long, String> memberIdFcmTokenMap = memberRepository.findAllById(memberIds).stream()
                .collect(Collectors.toMap(Member::getId, Member::getFcmToken));

        for (NotificationRecord notificationRecord : notificationRecords) {
            String fcmToken = memberIdFcmTokenMap.get(notificationRecord.getMemberId());
            eventPublisher.publishEvent(FcmNotificationDTO.of(
                    notificationRecord.getTitle(), notificationRecord.getBody(), fcmToken,
                    notificationRecord.getNotificationType(), notificationRecord.getMemberId(), notificationRecord.getDogId()
            ));
        }

        List<Long> notificationRecordIds = notificationRecords.stream()
                .map(NotificationRecord::getId)
                .collect(Collectors.toList());

        notificationRecordQueryRepository.deleteByIds(notificationRecordIds);
    }
}
