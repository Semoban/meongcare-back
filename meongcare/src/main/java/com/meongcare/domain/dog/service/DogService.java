package com.meongcare.domain.dog.service;

import com.meongcare.domain.auth.domain.entity.Member;
import com.meongcare.domain.auth.domain.repository.MemberRepository;
import com.meongcare.domain.dog.domain.DogRepository;
import com.meongcare.domain.dog.domain.entity.Dog;
import com.meongcare.domain.dog.presentation.dto.request.PutDogRequestDto;
import com.meongcare.domain.dog.presentation.dto.request.SaveDogRequestDto;
import com.meongcare.domain.dog.presentation.dto.response.GetDogResponseDto;
import com.meongcare.domain.dog.presentation.dto.response.GetDogsResponseDto;
import com.meongcare.domain.weight.domain.entity.Weight;
import com.meongcare.domain.weight.domain.repository.WeightRepository;
import com.meongcare.infra.image.ImageDirectory;
import com.meongcare.infra.image.ImageHandler;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;


@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class DogService {

    private final MemberRepository memberRepository;
    private final DogRepository dogRepository;
    private final WeightRepository weightRepository;
    private final ImageHandler imageHandler;

    @Transactional
    public void saveDog(MultipartFile multipartFile, SaveDogRequestDto saveDogRequestDto, Long userId) {
        Member member = memberRepository.findByUserId(userId);
        String dogImageURL = imageHandler.uploadImage(multipartFile, ImageDirectory.MEDICAL_RECORD);

        Dog dog = saveDogRequestDto.toEntity(member, dogImageURL);
        dogRepository.save(dog);

        Weight weight = Weight.createWeight(dog.getWeight(), userId);
        weightRepository.save(weight);
    }

    public GetDogsResponseDto getDogs(Long userId) {
        Dog dog = dogRepository.getById(userId);
        return GetDogsResponseDto.of(
                dog.getId(),
                dog.getName(),
                dog.getImageUrl()
        );
    }

    public GetDogResponseDto getDog(Long dogId) {
        Dog dog = dogRepository.getById(dogId);

        return GetDogResponseDto
                .builder()
                .dogId(dogId)
                .name(dog.getName())
                .imageUrl(dog.getImageUrl())
                .type(dog.getType())
                .sex(dog.getSex().getSex())
                .castrate(dog.isCastrate())
                .birthDate(dog.getBirthDate())
                .neckRound(dog.getNeckRound())
                .chestRound(dog.getChestRound())
                .weight(dog.getWeight())
                .build();
    }

    @Transactional
    public void updateDog(MultipartFile multipartFile, PutDogRequestDto putDogRequestDto, Long dogId) {
        Dog dog = dogRepository.getById(dogId);
        String dogImageURL = imageHandler.uploadImage(multipartFile, ImageDirectory.MEDICAL_RECORD);
        dog.updateAll(putDogRequestDto, dogImageURL);
    }
}
