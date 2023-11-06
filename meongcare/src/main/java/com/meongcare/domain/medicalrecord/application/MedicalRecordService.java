package com.meongcare.domain.medicalrecord.application;

import com.meongcare.domain.dog.domain.DogRepository;
import com.meongcare.domain.dog.domain.entity.Dog;
import com.meongcare.domain.medicalrecord.domain.entity.MedicalRecord;
import com.meongcare.domain.medicalrecord.domain.repository.MedicalRecordQueryRepository;
import com.meongcare.domain.medicalrecord.domain.repository.MedicalRecordRepository;
import com.meongcare.domain.medicalrecord.domain.repository.vo.GetMedicalRecordsVo;
import com.meongcare.domain.medicalrecord.presentation.dto.request.PutMedicalRecordRequestDto;
import com.meongcare.domain.medicalrecord.presentation.dto.request.SaveMedicalRecordRequestDto;
import com.meongcare.domain.medicalrecord.presentation.dto.response.GetMedicalRecordResponseDto;
import com.meongcare.domain.medicalrecord.presentation.dto.response.GetMedicalRecordsResponseDto;
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
    public void save(MultipartFile multipartFile, SaveMedicalRecordRequestDto saveMedicalRecordRequestDto) {
        Dog dog = dogRepository.getById(saveMedicalRecordRequestDto.getDogId());
        String imageURL = imageHandler.uploadImage(multipartFile, ImageDirectory.MEDICAL_RECORD);

        medicalRecordRepository.save(saveMedicalRecordRequestDto.toEntity(dog, imageURL));
    }

    @Transactional
    public void update(MultipartFile multipartFile, PutMedicalRecordRequestDto putMedicalRecordRequestDto) {
        MedicalRecord medicalRecord = medicalRecordRepository.getById(putMedicalRecordRequestDto.getMedicalRecordId());
        String imageURL = imageHandler.uploadImage(multipartFile, ImageDirectory.MEDICAL_RECORD);

        medicalRecord.updateMedicalRecord(putMedicalRecordRequestDto, imageURL);
    }

    @Transactional
    public void deleteMedicalRecords(List<Long> medicalRecordIds) {
        List<MedicalRecord> medicalRecords = medicalRecordQueryRepository.getByIds(medicalRecordIds);
        for (MedicalRecord medicalRecord : medicalRecords) {
            imageHandler.deleteImage(medicalRecord.getImageUrl());
        }

        medicalRecordQueryRepository.deleteByIds(medicalRecordIds);
    }


    public GetMedicalRecordResponseDto get(Long medicalRecordId) {
        MedicalRecord medicalRecord = medicalRecordRepository.getById(medicalRecordId);
        return GetMedicalRecordResponseDto.of(medicalRecord);
    }

    public GetMedicalRecordsResponseDto getMedicalRecords(LocalDateTime dateTime) {
        List<GetMedicalRecordsVo> getMedicalRecordsVos = medicalRecordQueryRepository.getByDate(dateTime);
        return GetMedicalRecordsResponseDto.of(getMedicalRecordsVos);

    }
}
