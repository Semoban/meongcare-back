package com.meongcare.domain.supplements.application;

import com.meongcare.common.util.LocalDateTimeUtils;
import com.meongcare.domain.dog.domain.DogRepository;
import com.meongcare.domain.dog.domain.entity.Dog;
import com.meongcare.domain.supplements.domain.entity.Supplements;
import com.meongcare.domain.supplements.domain.entity.SupplementsRecord;
import com.meongcare.domain.supplements.domain.entity.SupplementsTime;
import com.meongcare.domain.supplements.domain.repository.SupplementsRecordRepository;
import com.meongcare.domain.supplements.domain.repository.SupplementsRepository;
import com.meongcare.domain.supplements.domain.repository.SupplementsTimeRepository;
import com.meongcare.domain.supplements.presentation.dto.request.SaveSupplementsRequest;
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

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SupplementsService {

    private final SupplementsRepository supplementsRepository;
    private final SupplementsTimeRepository supplementsTimeRepository;
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
        }
    }

    private List<LocalDate> createIntakeDays(LocalDate startDate, LocalDate lastDate, int intakeCycle){
        List<LocalDate> intakeDays = new ArrayList<>();
        LocalDate intakeDate = startDate;
        lastDate = lastDate.plusDays(1);
        while (intakeDate.isBefore(lastDate)){
            intakeDays.add(intakeDate);
            intakeDate = intakeDate.plusDays(intakeCycle);
        }
        return intakeDays;
    }
}
