package com.meongcare.domain.supplements.application;

import com.meongcare.domain.dog.domain.DogRepository;
import com.meongcare.domain.dog.domain.entity.Dog;
import com.meongcare.domain.supplements.domain.entity.Supplements;
import com.meongcare.domain.supplements.domain.entity.SupplementsRecord;
import com.meongcare.domain.supplements.domain.entity.SupplementsTime;
import com.meongcare.domain.supplements.domain.repository.*;
import com.meongcare.domain.supplements.domain.repository.vo.GetSupplementsRoutineVO;
import com.meongcare.domain.supplements.domain.repository.vo.GetSupplementsRoutineWithoutStatusVO;
import com.meongcare.domain.supplements.presentation.dto.request.SaveSupplementsRequest;
import com.meongcare.domain.supplements.presentation.dto.response.*;
import com.meongcare.infra.image.ImageDirectory;
import com.meongcare.infra.image.ImageHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
        Supplements supplements = supplementsRepository.getById(supplementsId);
        List<SupplementsTime> supplementsTimes = supplementsTimeRepository.findAllBySupplementsId(supplementsId);
        return GetSupplementsInfoResponse.of(supplements, supplementsTimes);
    }

    @Transactional
    public void stopSupplementsRoutine(Long supplementsId, boolean isActive) {
        Supplements supplements = supplementsRepository.getById(supplementsId);
        supplements.updateActive(isActive);
    }

    @Transactional
    public void deleteSupplements(Long supplementsId) {
        Supplements supplements = supplementsRepository.getById(supplementsId);
        deleteSupplementsTimes(supplementsId);
        supplements.delete();
    }

    @Transactional
    public void deleteSupplements(List<Long> supplementsIds) {
        for (Long supplementsId : supplementsIds) {
            Supplements supplements = supplementsRepository.getById(supplementsId);
            supplements.delete();
            deleteSupplementsTimes(supplementsId);
        }
    }

    private void deleteSupplementsTimes(Long supplementsId) {
        List<SupplementsTime> allBySupplementsId = supplementsTimeRepository.findAllBySupplementsId(supplementsId);
        for (SupplementsTime supplementsTime : allBySupplementsId) {
            supplementsTime.delete();
        }
    }

    @Transactional
    public void updatePushAgreement(Long supplementsId, boolean pushAgreement) {
        Supplements supplements = supplementsRepository.getById(supplementsId);
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
        List<Supplements> supplements = supplementsRepository.findAllByDogId(dogId);
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
}
