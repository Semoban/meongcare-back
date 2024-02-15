package com.meongcare.common.event;

import com.meongcare.domain.notifciation.domain.dto.FcmNotificationDTO;
import com.meongcare.infra.image.ImageHandler;
import com.meongcare.infra.message.MessageHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class EventListener {

    private final ImageHandler imageHandler;
    private final MessageHandler messageHandler;

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT, fallbackExecution = true)
    public void deleteImage(String imageURL) {
        imageHandler.deleteImage(imageURL);
    }

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT, fallbackExecution = true)
    public void sendFcmMessage(FcmNotificationDTO fcmNotificationDTO) {
        messageHandler.sendMessage(
                fcmNotificationDTO.getTitle(), fcmNotificationDTO.getBody(), fcmNotificationDTO.getFcmToken(),
                fcmNotificationDTO.getNotificationType(), fcmNotificationDTO.getMemberId(), fcmNotificationDTO.getDogId()
        );
    }
}
