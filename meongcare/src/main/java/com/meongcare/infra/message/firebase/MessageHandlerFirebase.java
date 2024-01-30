package com.meongcare.infra.message.firebase;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import com.meongcare.common.error.ErrorCode;
import com.meongcare.common.error.exception.serverError.MessageException;
import com.meongcare.infra.message.MessageHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class MessageHandlerFirebase implements MessageHandler {

    private final FirebaseMessaging firebaseMessaging;

    @Override
    public void sendMessage(String title, String body, String fcmToken) {
        Notification notification = createNotification(title, body);
        Message message = createMessage(fcmToken, notification);
        sendMessage(message);
    }

    private void sendMessage(Message message) {
        try{
            firebaseMessaging.send(message);
        } catch (FirebaseMessagingException e) {
            log.error("errorMessage = {}", e.getMessage());
            throw new MessageException(ErrorCode.FAILED_MESSAGE_SEND);
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
