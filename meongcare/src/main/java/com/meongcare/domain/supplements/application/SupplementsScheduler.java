package com.meongcare.domain.supplements.application;

import com.meongcare.common.util.LocalDateTimeUtils;
import com.meongcare.domain.supplements.domain.entity.Supplements;
import com.meongcare.domain.supplements.domain.entity.SupplementsRecord;
import com.meongcare.domain.supplements.domain.entity.SupplementsTime;
import com.meongcare.domain.supplements.domain.repository.*;
import com.meongcare.domain.supplements.domain.repository.vo.GetAlarmSupplementsVO;
import com.meongcare.domain.supplements.domain.repository.vo.GetSupplementsAndTimeVO;
import com.meongcare.infra.message.MessageHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class SupplementsScheduler {

    @Value("${env.image-url.logo}")
    private String logoImageUrl;

    private final MessageHandler messageHandler;
    private final SupplementsRecordQueryRepository supplementsRecordQueryRepository;
    private final SupplementsTimeQueryRepository supplementsTimeQueryRepository;
    private final SupplementsRecordJdbcRepository supplementsRecordJdbcRepository;

    private static final String DEFAULT_ALARM_BODY_TEXT = "의 건강을 챙겨주세요!";

    @Transactional
    @Scheduled(cron = "0 55 23 * * *", zone = "Asia/Seoul")
    public void createAllSupplements() {
        List<GetSupplementsAndTimeVO> supplementsAndTimeVOS = supplementsTimeQueryRepository.findAll();
        List<SupplementsRecord> supplementsRecords = supplementsAndTimeVOS.stream()
                .map(supplementsAndTimeVO -> createSupplementsRecord(supplementsAndTimeVO))
                .filter(supplementsRecord -> supplementsRecord != null)
                .collect(Collectors.toList());
        supplementsRecordJdbcRepository.saveSupplementsRecords(supplementsRecords);
        log.info("영양제 당일 데이터 추가 성공");
    }

    @Transactional(readOnly = true)
    @Scheduled(cron = "2 * * * * *", zone = "Asia/Seoul")
    public void sendSupplementsAlarm() {
        LocalTime now = LocalDateTimeUtils.createNowWithZeroSecond();
        LocalTime fiftyNineSecondsLater = LocalDateTimeUtils.createFiftyNineSecondsLater(now);
        List<GetAlarmSupplementsVO> alarmSupplementsVOS = supplementsRecordQueryRepository.findAllAlarmSupplementsByTime(now, fiftyNineSecondsLater);

        for (GetAlarmSupplementsVO alarmSupplementsVO : alarmSupplementsVOS) {
            String title = createPushAlarmTitle(alarmSupplementsVO.getDogName(), now, alarmSupplementsVO.getSupplementsName());
            String body = createPushAlarmBody(alarmSupplementsVO.getDogName());
            String fcmToken = alarmSupplementsVO.getFcmToken();
            messageHandler.sendMessage(title, body, logoImageUrl, fcmToken);
        }
    }

    private String createPushAlarmBody(String dogName) {
        String body = dogName + DEFAULT_ALARM_BODY_TEXT;
        return body;
    }

    private String createPushAlarmTitle(String dogName, LocalTime now, String supplementsName) {
        String title = String.join(" ", dogName, LocalDateTimeUtils.createAMPMTime(now), supplementsName);
        return title;
    }


    private SupplementsRecord createSupplementsRecord(GetSupplementsAndTimeVO supplementsAndTimeVO) {
        LocalDate createDate = LocalDate.now().plusDays(1);
        Supplements supplements = supplementsAndTimeVO.getSupplements();
        SupplementsTime supplementsTime = supplementsAndTimeVO.getSupplementsTime();

        if (checkIntakeDate(supplements.getStartDate(), createDate, supplements.getIntakeCycle())) {
            return SupplementsRecord.of(supplements, supplementsTime, createDate);
        }
        return null;
    }

    private boolean checkIntakeDate(LocalDate checkDate, LocalDate createDate, int intakeCycle) {
        while (!createDate.isEqual(checkDate) && createDate.isAfter(checkDate)) {
            checkDate = checkDate.plusDays(intakeCycle);
        }
        if (createDate.isEqual(checkDate)) {
            return true;
        }
        return false;
    }
}
