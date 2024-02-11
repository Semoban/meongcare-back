package com.meongcare.domain.medicalrecord.application;

import com.meongcare.common.util.LocalDateTimeUtils;
import com.meongcare.domain.dog.domain.DogRepository;
import com.meongcare.domain.dog.domain.entity.Dog;
import com.meongcare.domain.medicalrecord.domain.entity.MedicalRecord;
import com.meongcare.domain.medicalrecord.domain.repository.MedicalRecordQueryRepository;
import com.meongcare.domain.medicalrecord.domain.repository.MedicalRecordRepository;
import com.meongcare.domain.medicalrecord.domain.repository.vo.GetMedicalRecordsVo;
import com.meongcare.domain.medicalrecord.presentation.dto.request.PutMedicalRecordRequest;
import com.meongcare.domain.medicalrecord.presentation.dto.request.SaveMedicalRecordRequest;
import com.meongcare.domain.medicalrecord.presentation.dto.response.GetMedicalRecordResponse;
import com.meongcare.domain.medicalrecord.presentation.dto.response.GetMedicalRecordsResponse;
import com.meongcare.infra.image.ImageDirectory;
import com.meongcare.infra.image.ImageHandler;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class MedicalRecordService {

    private final DogRepository dogRepository;
    private final ImageHandler imageHandler;
    private final MedicalRecordRepository medicalRecordRepository;
    private final MedicalRecordQueryRepository medicalRecordQueryRepository;

    @Transactional
    public void save(MultipartFile multipartFile, SaveMedicalRecordRequest saveMedicalRecordRequest) {
        Dog dog = dogRepository.getDog(saveMedicalRecordRequest.getDogId());
        String imageURL = imageHandler.uploadImage(multipartFile, ImageDirectory.MEDICAL_RECORD);

        medicalRecordRepository.save(saveMedicalRecordRequest.toEntity(dog, imageURL));
    }

    @Transactional
    public void update(MultipartFile multipartFile, PutMedicalRecordRequest putMedicalRecordRequest) {
        MedicalRecord medicalRecord = medicalRecordRepository.getById(putMedicalRecordRequest.getMedicalRecordId());
        String imageURL = imageHandler.uploadImage(multipartFile, ImageDirectory.MEDICAL_RECORD);

        medicalRecord.updateMedicalRecord(putMedicalRecordRequest, imageURL);
    }

    @Transactional
    public void deleteMedicalRecords(List<Long> medicalRecordIds) {
        List<MedicalRecord> medicalRecords = medicalRecordQueryRepository.getByIds(medicalRecordIds);
        for (MedicalRecord medicalRecord : medicalRecords) {
            imageHandler.deleteImage(medicalRecord.getImageUrl());
        }

        medicalRecordQueryRepository.deleteByIds(medicalRecordIds);
    }

    public GetMedicalRecordResponse get(Long medicalRecordId) {
        MedicalRecord medicalRecord = medicalRecordRepository.getById(medicalRecordId);
        return GetMedicalRecordResponse.of(medicalRecord);
    }

    public GetMedicalRecordsResponse getMedicalRecords(Long dogId, LocalDateTime dateTime) {

        List<GetMedicalRecordsVo> getMedicalRecordsVos = medicalRecordQueryRepository.getByDate(
                dogId,
                LocalDateTimeUtils.createNowMidnight(dateTime),
                LocalDateTimeUtils.createNextMidnight(dateTime)
        );
        return GetMedicalRecordsResponse.of(getMedicalRecordsVos);

    }
}
