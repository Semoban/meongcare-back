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
    public void save(SaveMedicalRecordRequest saveMedicalRecordRequest) {
        Dog dog = dogRepository.getDog(saveMedicalRecordRequest.getDogId());
        medicalRecordRepository.save(saveMedicalRecordRequest.toEntity(dog));
    }

    @Transactional
    public void update(PutMedicalRecordRequest putMedicalRecordRequest) {
        MedicalRecord medicalRecord = medicalRecordRepository.getById(putMedicalRecordRequest.getMedicalRecordId());
        medicalRecord.updateMedicalRecord(putMedicalRecordRequest);
    }

    @Transactional
    public void deleteMedicalRecords(List<Long> medicalRecordIds) {
        List<MedicalRecord> medicalRecords = medicalRecordQueryRepository.getByIds(medicalRecordIds);
        for (MedicalRecord medicalRecord : medicalRecords) {
            imageHandler.deleteImage(medicalRecord.getImageUrl());
        }

        medicalRecordQueryRepository.deleteMedicalRecords(medicalRecordIds);
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
