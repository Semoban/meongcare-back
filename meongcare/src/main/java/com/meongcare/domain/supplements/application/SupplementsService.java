package com.meongcare.domain.supplements.application;

import com.meongcare.domain.dog.domain.DogRepository;
import com.meongcare.domain.dog.domain.entity.Dog;
import com.meongcare.domain.supplements.domain.entity.Supplements;
import com.meongcare.domain.supplements.domain.entity.SupplementsRecord;
import com.meongcare.domain.supplements.domain.entity.SupplementsTime;
import com.meongcare.domain.supplements.domain.repository.*;
import com.meongcare.domain.supplements.domain.repository.vo.GetSupplementsAndTimeVO;
import com.meongcare.domain.supplements.domain.repository.vo.GetSupplementsRoutineVO;
import com.meongcare.domain.supplements.domain.repository.vo.GetSupplementsRoutineWithoutStatusVO;
import com.meongcare.domain.supplements.presentation.dto.request.SaveSupplementsRequest;
import com.meongcare.domain.supplements.presentation.dto.response.GetSupplementsRateResponse;
import com.meongcare.domain.supplements.presentation.dto.response.GetSupplementsResponse;
import com.meongcare.domain.supplements.presentation.dto.response.GetSupplementsRoutineResponse;
import com.meongcare.infra.image.ImageDirectory;
import com.meongcare.infra.image.ImageHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SupplementsService {

    private final SupplementsRepository supplementsRepository;
    private final SupplementsTimeRepository supplementsTimeRepository;
    private final SupplementsTimeQueryRepository supplementsTimeQueryRepository;
    private final SupplementsRecordRepository supplementsRecordRepository;
    private final SupplementsRecordQueryRepository supplementsRecordQueryRepository;
    private final DogRepository dogRepository;
    private final ImageHandler imageHandler;

    @Transactional
    public void saveSupplements(SaveSupplementsRequest saveSupplementsRequest, MultipartFile multipartFile) {
        Dog dog = dogRepository.getById(saveSupplementsRequest.getDogId());
        String imageURL = imageHandler.uploadImage(multipartFile, ImageDirectory.EXCRETA);

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
        int supplementsRate = supplementsRecordQueryRepository.calSupplementsRate(dogId, date);
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

    public GetSupplementsResponse getSupplementsInfo(Long supplementsId) {
        Supplements supplements = supplementsRepository.getById(supplementsId);
        List<SupplementsTime> supplementsTimes = supplementsTimeRepository.findAllBySupplementsId(supplementsId);
        return GetSupplementsResponse.of(supplements, supplementsTimes);
    }

    private int calSupplementsRate(List<SupplementsRecord> supplementsRecords) {
        int intakeStatusCount = calIntakeStatusCount(supplementsRecords);
        int supplementsRate = (intakeStatusCount * 100) / supplementsRecords.size();
        return supplementsRate;
    }

    private int calIntakeStatusCount(List<SupplementsRecord> supplementsRecords) {
        int intakeStatusCount = 0;
        for (SupplementsRecord supplementsRecord : supplementsRecords) {
            if (supplementsRecord.isIntakeStatus()) {
                intakeStatusCount++;
            }
        }
        return intakeStatusCount;
    }

    private GetSupplementsRoutineResponse createBeforeRecord(LocalDate date, Long dogId) {
        List<GetSupplementsRoutineVO> getSupplementsRoutineVOs = supplementsRecordQueryRepository.getByDogIdAndDate(dogId, date);
        return GetSupplementsRoutineResponse.createBeforeRecord(getSupplementsRoutineVOs);
    }

    private GetSupplementsRoutineResponse createAfterRecord(LocalDate date, Long dogId) {
        List<Supplements> supplements = supplementsRepository.findAllByDogId(dogId);
        List<GetSupplementsRoutineWithoutStatusVO> getSupplementsRoutineWithoutStatusVOs = new ArrayList<>();
        for (Supplements supplementInfo : supplements) {
            if (checkIntakeDate(supplementInfo.getStartDate(), date)) {
                List<GetSupplementsRoutineWithoutStatusVO> getSupplementsRoutineWithoutStatusVO = supplementsTimeQueryRepository.findBySupplementsId(supplementInfo.getId());
                getSupplementsRoutineWithoutStatusVOs.addAll(getSupplementsRoutineWithoutStatusVO);
            }
        }
        return GetSupplementsRoutineResponse.createAfterRecord(getSupplementsRoutineWithoutStatusVOs);
    }

    @Transactional
    @Scheduled(cron = "0 55 23 * * *", zone = "Asia/Seoul")
    public void createAllSupplements() {
        List<GetSupplementsAndTimeVO> supplementsAndTimeVOS = supplementsTimeQueryRepository.findAll();
        for (GetSupplementsAndTimeVO supplementsAndTimeVO : supplementsAndTimeVOS) {
            createSupplements(supplementsAndTimeVO);
        }
        log.info("영양제 당일 데이터 추가 성공");
    }

    private void createSupplements(GetSupplementsAndTimeVO supplementsAndTimeVO) {
        LocalDate now = LocalDate.now().plusDays(1);
        Supplements supplements = supplementsAndTimeVO.getSupplements();
        SupplementsTime supplementsTime = supplementsAndTimeVO.getSupplementsTime();

        if (checkIntakeDate(supplements.getStartDate(), now)) {
            supplementsRecordRepository.save(SupplementsRecord.of(supplements, supplementsTime, now));
        }
    }

    private boolean checkIntakeDate(LocalDate checkDate, LocalDate now) {
        while (!now.isEqual(checkDate) && now.isAfter(checkDate)) {
            checkDate = checkDate.plusDays(3);
        }
        if (now.isEqual(checkDate)) {
            return true;
        }
        return false;
    }

}
