package com.meongcare.domain.dog.service;

import com.meongcare.domain.auth.domain.entity.Member;
import com.meongcare.domain.auth.domain.repository.MemberRepository;
import com.meongcare.domain.dog.domain.DogRepository;
import com.meongcare.domain.dog.domain.DogWeightRepository;
import com.meongcare.domain.dog.domain.entity.Dog;
import com.meongcare.domain.dog.domain.entity.DogWeight;
import com.meongcare.domain.dog.presentation.dto.request.SaveDogRequestDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Service
@AllArgsConstructor
public class DogService {

    private final MemberRepository memberRepository;
    private final DogRepository dogRepository;
    private final DogWeightRepository dogWeightRepository;

    @Transactional
    public void saveDog(Long userId, SaveDogRequestDto saveDogRequestDto) {

        Member member = memberRepository
                .findById(userId)
                .orElseThrow(IllegalArgumentException::new);

        double weight = saveDogRequestDto.getWeight();

        Dog dog = saveDogRequestDto.toEntity(member);
        Dog createDog = dogRepository.save(dog);

        DogWeight dogWeight = DogWeight.builder()
                .weight(weight)
                .dog(createDog)
                .build();

        dogWeightRepository.save(dogWeight);

    }
}
