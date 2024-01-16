package com.meongcare.infra.message;

public interface MessageHandler {

    void sendMessage(String title, String body, String logoImageUrl, String fcmToken);

}
