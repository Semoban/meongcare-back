package com.meongcare.domain.dog.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;


@Component
@RequiredArgsConstructor
@Slf4j
@Transactional
public class DogScheduler {

    private final DogService dogService;

    @Scheduled(cron = "0 0 0 * * *")
    public void deleteOwnerLessDogs() {
        log.info("매일 자정 주인 없는 강아지 삭제");
        dogService.deleteOwnerLessDogs();
    }
}
