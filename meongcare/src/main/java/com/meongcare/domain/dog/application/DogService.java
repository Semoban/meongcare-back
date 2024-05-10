package com.meongcare.domain.dog.application;

import com.meongcare.domain.dog.domain.entity.MemberDog;
import com.meongcare.domain.dog.domain.entity.Sex;
import com.meongcare.domain.dog.domain.repository.DogQueryRepository;
import com.meongcare.domain.dog.domain.repository.MemberDogQueryRepository;
import com.meongcare.domain.dog.domain.repository.MemberDogRepository;
import com.meongcare.domain.excreta.domain.repository.ExcretaQueryRepository;
import com.meongcare.domain.feed.domain.repository.FeedQueryRepository;
import com.meongcare.domain.feed.domain.repository.FeedRecordQueryRepository;
import com.meongcare.domain.medicalrecord.domain.repository.MedicalRecordQueryRepository;
import com.meongcare.domain.member.domain.entity.Member;
import com.meongcare.domain.member.domain.repository.MemberRepository;
import com.meongcare.domain.dog.domain.repository.DogRepository;
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
    private final DogQueryRepository dogQueryRepository;
    private final MemberDogQueryRepository memberDogQueryRepository;
    private final MemberDogRepository memberDogRepository;
    private final WeightRepository weightRepository;
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
        Dog dog = saveDogRequest.toEntity();
        dogRepository.save(dog);
        MemberDog memberDog = MemberDog.of(member, dog);
        memberDogRepository.save(memberDog);

        Weight weight = Weight.createWeight(dog.getWeight(), dog);
        weightRepository.save(weight);
    }

    public GetDogsResponse getDogs(Long memberId) {
        List<Long> dogIds = memberDogQueryRepository.findDogIdsByMember(memberId);
        List<Dog> dogs = dogQueryRepository.findAllByIds(dogIds);
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
    public void deleteMemberDog(Long dogId, Long memberId) {
        memberDogQueryRepository.deleteByMemberAndDog(memberId, dogId);
    }

    @Transactional
    public void deleteOwnerLessDogs() {
        List<Dog> dogs = dogRepository.findAll();
        for (Dog dog : dogs) {
            List<MemberDog> dogOwner = memberDogQueryRepository.findAllByDogId(dog.getId());
            if (dogOwner.isEmpty()) {
                deleteDog(dog.getId());
            }
        }
    }


    //해당 dog의 memberDog이 모두 삭제된 경우만 사용함
    @Transactional
    public void deleteDog(Long dogId) {
        Dog dog = dogRepository.getDog(dogId);

        symptomQueryRepository.deleteSymptomDogId(dogId);
        medicalRecordQueryRepository.deleteMedicalRecordsDogId(dogId);
        weightQueryRepository.deleteWeightByDogId(dogId);
        excretaQueryRepository.deleteExcretaByDogId(dogId);

        feedQueryRepository.deleteFeedByDogId(dogId);
        feedRecordQueryRepository.deleteFeedRecordByDogId(dogId);

        List<Long> supplementsIds = supplementsQueryRepository.getSupplementsIdsByDogId(dogId);
        supplementsService.deleteSupplementsList(supplementsIds);

        dog.softDelete();
    }
}
