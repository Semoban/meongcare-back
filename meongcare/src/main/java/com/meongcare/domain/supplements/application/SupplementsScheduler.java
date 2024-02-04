package com.meongcare.domain.supplements.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class SupplementsScheduler {
    private final SupplementsService supplementsService;

    @Scheduled(cron = "0 55 23 * * *", zone = "Asia/Seoul")
    public void createAllSupplements() {
        log.info("영양제 당일 데이터 추가");
        supplementsService.createAllSupplements();
    }

    @Scheduled(cron = "2 * * * * *", zone = "Asia/Seoul")
    public void sendSupplementsAlarm() {
        supplementsService.sendSupplementsAlarm();
    }
}
