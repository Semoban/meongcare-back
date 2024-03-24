package com.meongcare.domain.dog.application;

import com.meongcare.domain.dog.domain.entity.Sex;
import com.meongcare.domain.excreta.domain.repository.ExcretaQueryRepository;
import com.meongcare.domain.feed.domain.repository.FeedQueryRepository;
import com.meongcare.domain.feed.domain.repository.FeedRecordQueryRepository;
import com.meongcare.domain.medicalrecord.domain.repository.MedicalRecordQueryRepository;
import com.meongcare.domain.member.domain.entity.Member;
import com.meongcare.domain.member.domain.repository.MemberRepository;
import com.meongcare.domain.dog.domain.DogRepository;
import com.meongcare.domain.dog.domain.entity.Dog;
import com.meongcare.domain.dog.presentation.dto.request.PutDogRequest;
import com.meongcare.domain.dog.presentation.dto.request.SaveDogRequest;
import com.meongcare.domain.dog.presentation.dto.response.GetDogResponse;
import com.meongcare.domain.dog.presentation.dto.response.GetDogsResponse;
import com.meongcare.domain.supplements.application.SupplementsService;
import com.meongcare.domain.supplements.domain.repository.SupplementsQueryRepository;
import com.meongcare.domain.symptom.domain.repository.SymptomQueryRepository;
import com.meongcare.domain.weight.domain.entity.Weight;
import com.meongcare.domain.weight.domain.repository.WeightQueryRepository;
import com.meongcare.domain.weight.domain.repository.WeightRepository;
import com.meongcare.infra.image.ImageHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DogService {

    private final MemberRepository memberRepository;
    private final DogRepository dogRepository;
    private final WeightRepository weightRepository;
    private final ImageHandler imageHandler;
    private final SymptomQueryRepository symptomQueryRepository;
    private final FeedQueryRepository feedQueryRepository;
    private final MedicalRecordQueryRepository medicalRecordQueryRepository;
    private final ExcretaQueryRepository excretaQueryRepository;
    private final WeightQueryRepository weightQueryRepository;
    private final FeedRecordQueryRepository feedRecordQueryRepository;
    private final SupplementsQueryRepository supplementsQueryRepository;
    private final SupplementsService supplementsService;


    @Transactional
    public void saveDog(SaveDogRequest saveDogRequest, Long memberId) {
        Member member = memberRepository.getMember(memberId);
        Dog dog = saveDogRequest.toEntity(member);
        dogRepository.save(dog);

        Weight weight = Weight.createWeight(dog.getWeight(), dog);
        weightRepository.save(weight);
    }

    public GetDogsResponse getDogs(Long memberId) {
        Member member = memberRepository.getMember(memberId);
        List<Dog> dogs = dogRepository.findAllByMemberAndDeletedFalse(member);
        return GetDogsResponse.of(dogs);
    }

    public GetDogResponse getDog(Long dogId) {
        Dog dog = dogRepository.getDog(dogId);
        return GetDogResponse.from(dog);

    }

    @Transactional
    public void updateDog(PutDogRequest request, Long dogId) {
        Dog dog = dogRepository.getDog(dogId);
        dog.updateAll(
                request.getName(), request.getType(), request.getImageURL(), Sex.of(request.getSex()),
                request.isCastrate(), request.getBirthDate(), request.getBackRound(), request.getNeckRound(),
                request.getChestRound(), request.getWeight()
        );
    }

    @Transactional
    public void deleteDog(Long dogId) {
        Dog dog = dogRepository.getDog(dogId);

        symptomQueryRepository.deleteSymptomDogId(dogId);
        medicalRecordQueryRepository.deleteMedicalRecordsDogId(dogId);
        weightQueryRepository.deleteWeightByDogId(dogId);
        excretaQueryRepository.deleteExcretaByDogId(dogId);

        feedQueryRepository.deleteFeedByDogId(dogId);
        feedRecordQueryRepository.deleteFeedRecordByDogId(dogId);

        //영양제, 영양제 섭취 시간, 영양제 루틴
        List<Long> supplementsIds = supplementsQueryRepository.getSupplementsIdsByDogId(dogId);
        supplementsService.deleteSupplementsList(supplementsIds);

        dog.softDelete();
    }

}
