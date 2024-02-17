package com.meongcare.domain.notifciation.application;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NotificationScheduler {

    private final NotificationService notificationService;

    @Scheduled(cron = "1 * * * * *", zone = "Asia/Seoul")
    public void retryNotification() {
        notificationService.retryNotification();
    }
}
