package com.meongcare.domain.dog.application;

import com.meongcare.domain.member.domain.entity.Member;
import com.meongcare.domain.member.domain.repository.MemberRepository;
import com.meongcare.domain.dog.domain.DogRepository;
import com.meongcare.domain.dog.domain.entity.Dog;
import com.meongcare.domain.dog.presentation.dto.request.PutDogRequest;
import com.meongcare.domain.dog.presentation.dto.request.SaveDogRequest;
import com.meongcare.domain.dog.presentation.dto.response.GetDogResponse;
import com.meongcare.domain.dog.presentation.dto.response.GetDogsResponse;
import com.meongcare.domain.weight.domain.entity.Weight;
import com.meongcare.domain.weight.domain.repository.WeightRepository;
import com.meongcare.infra.image.ImageDirectory;
import com.meongcare.infra.image.ImageHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DogService {

    private final MemberRepository memberRepository;
    private final DogRepository dogRepository;
    private final WeightRepository weightRepository;
    private final ImageHandler imageHandler;

    @Transactional
    public void saveDog(MultipartFile multipartFile, SaveDogRequest saveDogRequest, Long userId) {
        Member member = memberRepository.getById(userId);
        String dogImageURL = imageHandler.uploadImage(multipartFile, ImageDirectory.DOG);

        Dog dog = saveDogRequest.toEntity(member, dogImageURL);
        dogRepository.save(dog);

        Weight weight = Weight.createWeight(dog.getWeight(), dog.getId());
        weightRepository.save(weight);
    }

    public GetDogsResponse getDogs(Long userId) {
        Dog dog = dogRepository.getById(userId);
        return GetDogsResponse.of(
                dog.getId(),
                dog.getName(),
                dog.getImageUrl()
        );
    }

    public GetDogResponse getDog(Long dogId) {
        Dog dog = dogRepository.getById(dogId);

        return GetDogResponse.from(dog);

    }

    @Transactional
    public void updateDog(MultipartFile multipartFile, PutDogRequest putDogRequest, Long dogId) {
        Dog dog = dogRepository.getById(dogId);
        String dogImageURL = imageHandler.uploadImage(multipartFile, ImageDirectory.DOG);
        dog.updateAll(putDogRequest, dogImageURL);
    }

    @Transactional
    public void deleteDog(Long dogId) {
        dogRepository.deleteById(dogId);
    }

}
