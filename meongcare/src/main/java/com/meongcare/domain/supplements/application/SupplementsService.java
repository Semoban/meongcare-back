package com.meongcare.domain.supplements.application;

import com.meongcare.domain.dog.domain.DogRepository;
import com.meongcare.domain.dog.domain.entity.Dog;
import com.meongcare.domain.supplements.domain.entity.Supplements;
import com.meongcare.domain.supplements.domain.entity.SupplementsRecord;
import com.meongcare.domain.supplements.domain.entity.SupplementsTime;
import com.meongcare.domain.supplements.domain.repository.*;
import com.meongcare.domain.supplements.domain.repository.vo.GetSupplementsAndTimeVO;
import com.meongcare.domain.supplements.domain.repository.vo.GetSupplementsRoutineVO;
import com.meongcare.domain.supplements.presentation.dto.request.SaveSupplementsRequest;
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
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SupplementsService {

    private final SupplementsRepository supplementsRepository;
    private final SupplementsTimeRepository supplementsTimeRepository;
    private final SupplementsQueryRepository supplementsQueryRepository;
    private final SupplementsTimeQueryRepository supplementsTimeQueryRepository;
    private final SupplementsRecordRepository supplementsRecordRepository;
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
        List<GetSupplementsRoutineVO> supplementsRoutineVOs = supplementsQueryRepository.getByDogId(dogId);

        return new GetSupplementsRoutineResponse();
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
        if (now.isEqual(checkDate)){
            return true;
        }
        return false;
    }
}
