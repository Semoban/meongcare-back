package com.meongcare.domain.supplements.application;

import com.meongcare.common.util.LocalDateTimeUtils;
import com.meongcare.domain.dog.domain.DogRepository;
import com.meongcare.domain.dog.domain.entity.Dog;
import com.meongcare.domain.notifciation.domain.dto.FcmNotificationDTO;
import com.meongcare.domain.notifciation.domain.entity.NotificationType;
import com.meongcare.domain.supplements.domain.entity.Supplements;
import com.meongcare.domain.supplements.domain.entity.SupplementsRecord;
import com.meongcare.domain.supplements.domain.entity.SupplementsTime;
import com.meongcare.domain.supplements.domain.repository.*;
import com.meongcare.domain.supplements.domain.repository.vo.GetAlarmSupplementsVO;
import com.meongcare.domain.supplements.domain.repository.vo.GetSupplementsAndTimeVO;
import com.meongcare.domain.supplements.domain.repository.vo.GetSupplementsRoutineVO;
import com.meongcare.domain.supplements.domain.repository.vo.GetSupplementsRoutineWithoutStatusVO;
import com.meongcare.domain.supplements.presentation.dto.request.SaveSupplementsRequest;
import com.meongcare.domain.supplements.presentation.dto.response.GetSupplementsInfoResponse;
import com.meongcare.domain.supplements.presentation.dto.response.GetSupplementsRateForHomeResponse;
import com.meongcare.domain.supplements.presentation.dto.response.GetSupplementsRateResponse;
import com.meongcare.domain.supplements.presentation.dto.response.GetSupplementsResponse;
import com.meongcare.domain.supplements.presentation.dto.response.GetSupplementsRoutineResponse;
import com.meongcare.infra.image.ImageDirectory;
import com.meongcare.infra.image.ImageHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SupplementsService {

    private final SupplementsRepository supplementsRepository;
    private final SupplementsTimeRepository supplementsTimeRepository;
    private final SupplementsTimeQueryRepository supplementsTimeQueryRepository;
    private final SupplementsQueryRepository supplementsQueryRepository;
    private final SupplementsRecordRepository supplementsRecordRepository;
    private final SupplementsRecordQueryRepository supplementsRecordQueryRepository;
    private final DogRepository dogRepository;
    private final ImageHandler imageHandler;
    private final SupplementsRecordJdbcRepository supplementsRecordJdbcRepository;
    private final ApplicationEventPublisher eventPublisher;

    private static final String ALARM_TITLE_TEXT_FORMAT = "%s %s";
    private static final String ALARM_BODY_TEXT_FORMAT = "%s에게 영양제를 챙겨주세요!";

    @Transactional
    public void saveSupplements(SaveSupplementsRequest saveSupplementsRequest, MultipartFile multipartFile) {
        Dog dog = dogRepository.getDog(saveSupplementsRequest.getDogId());
        String imageURL = imageHandler.uploadImage(multipartFile, ImageDirectory.SUPPLEMENTS);

        Supplements supplements = saveSupplementsRequest.toSupplements(dog, imageURL);
        supplementsRepository.save(supplements);

        List<SupplementsTime> supplementsTimes = saveSupplementsRequest.toSupplementsTimes(supplements);
        for (SupplementsTime supplementsTime : supplementsTimes) {
            supplementsTimeRepository.save(supplementsTime);
            supplementsRecordRepository.save(SupplementsRecord.of(supplements, supplementsTime, LocalDate.now()));
        }
    }

    public GetSupplementsRoutineResponse getSupplementsRoutine(LocalDate date, Long dogId) {
        if (date.isAfter(LocalDate.now())) {
            return createAfterRecord(date, dogId);
        }
        return createBeforeRecord(date, dogId);
    }

    public GetSupplementsRateResponse getSupplementsRate(LocalDate date, Long dogId) {
        if (date.isAfter(LocalDate.now())) {
            return GetSupplementsRateResponse.from(0);
        }
        int intakeRecordCount = supplementsRecordQueryRepository.getIntakeRecordCount(dogId, date);
        int totalRecordCount = supplementsRecordQueryRepository.getTotalRecordCount(dogId, date);
        int supplementsRate = calSupplementsRate(intakeRecordCount, totalRecordCount);
        return GetSupplementsRateResponse.from(supplementsRate);
    }

    @Transactional
    public void updateSupplementsIntakeStatus(Long supplementsRecordId) {
        SupplementsRecord supplementsRecord = supplementsRecordRepository.getById(supplementsRecordId);
        supplementsRecord.updateIntakeStatus();
    }

    @Transactional
    public void updateSupplementsTime(Long supplementsTimeId, LocalTime updateIntakeTime) {
        SupplementsTime supplementsTime = supplementsTimeRepository.getById(supplementsTimeId);
        supplementsTime.updateIntakeTIme(updateIntakeTime);
    }

    public GetSupplementsInfoResponse getSupplementsInfo(Long supplementsId) {
        Supplements supplements = supplementsRepository.getSupplement(supplementsId);
        List<SupplementsTime> supplementsTimes = supplementsTimeRepository.findAllBySupplementsId(supplementsId);
        return GetSupplementsInfoResponse.of(supplements, supplementsTimes);
    }

    @Transactional
    public void stopSupplementsRoutine(Long supplementsId, boolean isActive) {
        Supplements supplements = supplementsRepository.getSupplement(supplementsId);
        supplements.updateActive(isActive);
    }

    @Transactional
    public void deleteSupplements(Long supplementsId) {
        Supplements supplements = supplementsRepository.getSupplement(supplementsId);
        deleteSupplementsTimes(supplementsId);
        deleteSupplementsRecord(supplementsId);
        supplements.softDelete();
    }

    @Transactional
    public void deleteSupplementsList(List<Long> supplementsIds) {
        supplementsQueryRepository.deleteBySupplementsIds(supplementsIds);
        supplementsTimeQueryRepository.deleteBySupplementsIds(supplementsIds);
        supplementsRecordQueryRepository.deleteBySupplementsIds(supplementsIds);
    }

    private void deleteSupplementsRecord(Long supplementsId) {
        supplementsRecordQueryRepository.deleteBySupplementsId(supplementsId);
    }

    private void deleteSupplementsTimes(Long supplementsId) {
        supplementsTimeRepository.deleteBySupplementsId(supplementsId);
    }

    @Transactional
    public void updatePushAgreement(Long supplementsId, boolean pushAgreement) {
        Supplements supplements = supplementsRepository.getSupplement(supplementsId);
        supplements.updatePushAgreement(pushAgreement);
    }

    private boolean checkIntakeDate(LocalDate checkDate, LocalDate createSupplementsDate, int intakeCycle) {
        while (!createSupplementsDate.isEqual(checkDate) && createSupplementsDate.isAfter(checkDate)) {
            checkDate = checkDate.plusDays(intakeCycle);
        }
        if (createSupplementsDate.isEqual(checkDate)) {
            return true;
        }
        return false;
    }

    private int calSupplementsRate(int intakeStatusCount, int totalRecordCount) {
        if (totalRecordCount == 0) {
            return 0;
        }
        return (intakeStatusCount * 100) / totalRecordCount;
    }

    private GetSupplementsRoutineResponse createBeforeRecord(LocalDate date, Long dogId) {
        List<GetSupplementsRoutineVO> getSupplementsRoutineVOs = supplementsRecordQueryRepository.getByDogIdAndDate(dogId, date);
        return GetSupplementsRoutineResponse.createBeforeRecord(getSupplementsRoutineVOs);
    }

    private GetSupplementsRoutineResponse createAfterRecord(LocalDate date, Long dogId) {
        List<Supplements> supplements = supplementsRepository.findAllByDogIdAndDeletedFalse(dogId);
        List<GetSupplementsRoutineWithoutStatusVO> getSupplementsRoutineWithoutStatusVOs = new ArrayList<>();
        for (Supplements supplementInfo : supplements) {
            if (checkIntakeDate(supplementInfo.getStartDate(), date, supplementInfo.getIntakeCycle())) {
                List<GetSupplementsRoutineWithoutStatusVO> getSupplementsRoutineWithoutStatusVO = supplementsTimeQueryRepository.findBySupplementsId(supplementInfo.getId());
                getSupplementsRoutineWithoutStatusVOs.addAll(getSupplementsRoutineWithoutStatusVO);
            }
        }
        return GetSupplementsRoutineResponse.createAfterRecord(getSupplementsRoutineWithoutStatusVOs);
    }

    public GetSupplementsRateForHomeResponse getSupplementsRateForHome(Long dogId, LocalDate date) {
        int totalRecordCount = supplementsRecordQueryRepository.getTotalRecordCount(dogId, date);
        int isIntakeRecordCount = supplementsRecordQueryRepository.getIntakeRecordCount(dogId, date);

        return GetSupplementsRateForHomeResponse.of(isIntakeRecordCount, totalRecordCount);
    }

    public GetSupplementsResponse getSupplements(Long dogId) {
        List<Supplements> supplements = supplementsRepository.findAllByDogIdAndDeletedFalse(dogId);
        return GetSupplementsResponse.of(supplements);
    }

    @Transactional
    public void createAllSupplements() {
        List<GetSupplementsAndTimeVO> supplementsAndTimeVOS = supplementsTimeQueryRepository.findAll();
        List<SupplementsRecord> supplementsRecords = supplementsAndTimeVOS.stream()
                .map(this::createSupplementsRecord)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        supplementsRecordJdbcRepository.saveSupplementsRecords(supplementsRecords);
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

    public void sendSupplementsAlarm() {
        LocalTime now = LocalDateTimeUtils.createNowWithZeroSecond();
        LocalTime fiftyNineSecondsLater = LocalDateTimeUtils.createFiftyNineSecondsLater(now);
        List<GetAlarmSupplementsVO> alarmSupplementsVOS = supplementsRecordQueryRepository.findAllAlarmSupplementsByTime(now, fiftyNineSecondsLater);

        for (GetAlarmSupplementsVO alarmSupplementsVO : alarmSupplementsVOS) {
            String title = createPushAlarmTitle(now, alarmSupplementsVO.getSupplementsName());
            String body = createPushAlarmBody(alarmSupplementsVO.getDogName());
            String fcmToken = alarmSupplementsVO.getFcmToken();
            eventPublisher.publishEvent(FcmNotificationDTO.of(title, body, fcmToken,
                    NotificationType.SUPPLEMENTS, alarmSupplementsVO.getMemberId(), alarmSupplementsVO.getDogId()
            ));
        }
    }

    private String createPushAlarmBody(String dogName) {
        return String.format(ALARM_BODY_TEXT_FORMAT, dogName);
    }

    private String createPushAlarmTitle(LocalTime now, String supplementsName) {
        return String.format(ALARM_TITLE_TEXT_FORMAT, LocalDateTimeUtils.createAMPMTime(now), supplementsName);
    }
}
