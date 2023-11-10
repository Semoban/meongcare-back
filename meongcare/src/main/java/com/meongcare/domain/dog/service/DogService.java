package com.meongcare.domain.dog.service;

import com.meongcare.domain.auth.domain.entity.Member;
import com.meongcare.domain.auth.domain.repository.MemberRepository;
import com.meongcare.domain.dog.domain.DogRepository;
import com.meongcare.domain.dog.domain.DogWeightRepository;
import com.meongcare.domain.dog.domain.entity.Dog;
import com.meongcare.domain.dog.domain.entity.DogWeight;
import com.meongcare.domain.dog.presentation.dto.request.SaveDogRequestDto;
import com.meongcare.infra.image.ImageDirectory;
import com.meongcare.infra.image.ImageHandler;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;


@Service
@AllArgsConstructor
public class DogService {

    private final MemberRepository memberRepository;
    private final DogRepository dogRepository;
    private final DogWeightRepository dogWeightRepository;
    private final ImageHandler imageHandler;

    @Transactional
    public void saveDog(MultipartFile multipartFile, SaveDogRequestDto saveDogRequestDto, Long userId) {

        Member member = memberRepository.findByUserId(userId);
        String dogImageURL = imageHandler.uploadImage(multipartFile, ImageDirectory.MEDICAL_RECORD);
        double weight = saveDogRequestDto.getWeight();

        Dog dog = saveDogRequestDto.toEntity(member, dogImageURL);
        Dog createDog = dogRepository.save(dog);

        DogWeight dogWeight = DogWeight.of(createDog, weight);

        dogWeightRepository.save(dogWeight);

    }
}
