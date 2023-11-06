package com.meongcare.domain.medicalrecord.application;

import com.meongcare.domain.dog.domain.DogRepository;
import com.meongcare.domain.dog.domain.entity.Dog;
import com.meongcare.domain.medicalrecord.domain.entity.MedicalRecord;
import com.meongcare.domain.medicalrecord.domain.repository.MedicalRecordRepository;
import com.meongcare.domain.medicalrecord.presentation.dto.request.PutMedicalRecordRequestDto;
import com.meongcare.domain.medicalrecord.presentation.dto.request.SaveMedicalRecordRequestDto;
import com.meongcare.infra.image.ImageDirectory;
import com.meongcare.infra.image.ImageHandler;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class MedicalRecordService {

    private final DogRepository dogRepository;
    private final ImageHandler imageHandler;
    private final MedicalRecordRepository medicalRecordRepository;

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
    public void delete(Long medicalRecordId) {
        medicalRecordRepository.deleteById(medicalRecordId);
    }


}
