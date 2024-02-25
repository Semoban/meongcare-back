package com.meongcare.infra.message.firebase;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import com.meongcare.domain.member.domain.entity.Member;
import com.meongcare.domain.member.domain.repository.MemberRepository;
import com.meongcare.domain.notifciation.domain.entity.NotificationRecord;
import com.meongcare.domain.notifciation.domain.entity.NotificationType;
import com.meongcare.domain.notifciation.domain.repository.NotificationRecordRepository;
import com.meongcare.infra.message.MessageHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Component
public class MessageHandlerFirebase implements MessageHandler {

    private final FirebaseMessaging firebaseMessaging;
    private final NotificationRecordRepository notificationRecordRepository;
    private final MemberRepository memberRepository;

    @Transactional(propagation = Propagation.REQUIRES_NEW, noRollbackFor = FirebaseMessagingException.class)
    @Override
    public void sendMessage(String title, String body, String fcmToken,
                            NotificationType notificationType, Long memberId, Long dogId
    ) {
        if (fcmToken == null) {
            return;
        }

        Notification notification = createNotification(title, body);
        Message message = createMessage(fcmToken, notification);

        try {
            firebaseMessaging.send(message);
        } catch (FirebaseMessagingException e) {
            log.error("알림 보내기를 실패했습니다 errorMessage = {}", e.getMessage());
            Member member = memberRepository.getMember(memberId);
            member.deleteFcmToken();
        } catch (Exception e) {
            log.error("알림 보내기를 실패했습니다 errorMessage = {}", e.getMessage());
            notificationRecordRepository.save(new NotificationRecord(
                    notificationType, memberId, dogId,
                    title, body
            ));
        }
    }

    private Message createMessage(String fcmToken, Notification notification) {
        return Message.builder()
                .setToken(fcmToken)
                .setNotification(notification)
                .build();
    }

    private Notification createNotification(String title, String body) {
        return Notification.builder()
                .setTitle(title)
                .setBody(body)
                .build();
    }
}
