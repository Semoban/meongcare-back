package com.meongcare.domain.dog.application;

import com.meongcare.domain.excreta.domain.repository.ExcretaQueryRepository;
import com.meongcare.domain.excreta.domain.repository.ExcretaRepository;
import com.meongcare.domain.feed.domain.repository.FeedQueryRepository;
import com.meongcare.domain.feed.domain.repository.FeedRecordQueryRepository;
import com.meongcare.domain.feed.domain.repository.FeedRepository;
import com.meongcare.domain.feed.domain.repository.vo.GetFeedsVO;
import com.meongcare.domain.medicalrecord.domain.repository.MedicalRecordQueryRepository;
import com.meongcare.domain.medicalrecord.domain.repository.MedicalRecordRepository;
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
import com.meongcare.domain.supplements.domain.repository.SupplementsTimeQueryRepository;
import com.meongcare.domain.symptom.domain.repository.SymptomQueryRepository;
import com.meongcare.domain.symptom.domain.repository.SymptomRepository;
import com.meongcare.domain.weight.domain.entity.Weight;
import com.meongcare.domain.weight.domain.repository.WeightQueryRepository;
import com.meongcare.domain.weight.domain.repository.WeightRepository;
import com.meongcare.infra.image.ImageDirectory;
import com.meongcare.infra.image.ImageHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DogService {

    private final MemberRepository memberRepository;
    private final DogRepository dogRepository;
    private final WeightRepository weightRepository;
    private final ImageHandler imageHandler;

    private SymptomQueryRepository symptomQueryRepository;
    private FeedQueryRepository feedQueryRepository;
    private MedicalRecordQueryRepository medicalRecordQueryRepository;
    private ExcretaQueryRepository excretaQueryRepository;
    private WeightQueryRepository weightQueryRepository;
    private FeedRecordQueryRepository feedRecordQueryRepository;
    private SupplementsQueryRepository supplementsQueryRepository;
    private SupplementsTimeQueryRepository supplementsTimeQueryRepository;
    private SupplementsService supplementsService;


    @Transactional
    public void saveDog(MultipartFile multipartFile, SaveDogRequest saveDogRequest, Long userId) {
        Member member = memberRepository.getUser(userId);
        String dogImageURL = imageHandler.uploadImage(multipartFile, ImageDirectory.DOG);

        Dog dog = saveDogRequest.toEntity(member, dogImageURL);
        dogRepository.save(dog);

        Weight weight = Weight.createWeight(dog.getWeight(), dog);
        weightRepository.save(weight);
    }

    public GetDogsResponse getDogs(Long userId) {
        Member member = memberRepository.getUser(userId);
        List<Dog> dogs = dogRepository.findAllByMemberAndDeletedFalse(member);
        return GetDogsResponse.of(dogs);
    }

    public GetDogResponse getDog(Long dogId) {
        Dog dog = dogRepository.getDog(dogId);
        return GetDogResponse.from(dog);

    }

    @Transactional
    public void updateDog(MultipartFile multipartFile, PutDogRequest putDogRequest, Long dogId) {
        Dog dog = dogRepository.getDog(dogId);
        String dogImageURL = imageHandler.uploadImage(multipartFile, ImageDirectory.DOG);
        dog.updateAll(putDogRequest, dogImageURL);
    }

    @Transactional
    public void deleteDog(Long dogId) {
        Dog dog = dogRepository.getById(dogId);

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
