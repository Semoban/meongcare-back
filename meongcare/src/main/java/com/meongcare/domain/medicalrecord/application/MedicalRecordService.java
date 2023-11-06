package com.meongcare.domain.medicalrecord.application;

import com.meongcare.domain.dog.domain.DogRepository;
import com.meongcare.domain.dog.domain.entity.Dog;
import com.meongcare.domain.medicalrecord.domain.repository.MedicalRecordRepository;
import com.meongcare.domain.medicalrecord.presentation.dto.request.SaveMedicalRecordRequestDto;
import com.meongcare.infra.image.ImageDirectory;
import com.meongcare.infra.image.ImageHandler;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@AllArgsConstructor
public class MedicalRecordService {

    private final DogRepository dogRepository;
    private final ImageHandler imageHandler;
    private final MedicalRecordRepository medicalRecordRepository;

    public void save(MultipartFile multipartFile, SaveMedicalRecordRequestDto saveMedicalRecordRequestDto) {
        Dog dog = dogRepository.getById(saveMedicalRecordRequestDto.getDogId());
        String imageURL = imageHandler.uploadImage(multipartFile, ImageDirectory.MEDICAL_RECORD);

        medicalRecordRepository.save(saveMedicalRecordRequestDto.toEntity(dog, imageURL));
    }
}
