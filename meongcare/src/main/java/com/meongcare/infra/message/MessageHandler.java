package com.meongcare.infra.message;

import com.meongcare.domain.notifciation.domain.entity.NotificationType;

public interface MessageHandler {

    void sendMessage(String title, String body, String fcmToken, NotificationType notificationType, Long memberId, Long dogId);


}
