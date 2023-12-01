package com.meongcare.domain.dog.application;

import com.meongcare.common.util.LocalDateTimeUtils;
import com.meongcare.domain.dog.presentation.dto.response.GetAllRecordOfDogResponse;
import com.meongcare.domain.excreta.domain.repository.ExcretaQueryRepository;
import com.meongcare.domain.excreta.domain.repository.vo.GetExcretaVO;
import com.meongcare.domain.feed.domain.repository.FeedRecordQueryRepository;
import com.meongcare.domain.member.domain.entity.Member;
import com.meongcare.domain.member.domain.repository.MemberRepository;
import com.meongcare.domain.dog.domain.DogRepository;
import com.meongcare.domain.dog.domain.entity.Dog;
import com.meongcare.domain.dog.presentation.dto.request.PutDogRequest;
import com.meongcare.domain.dog.presentation.dto.request.SaveDogRequest;
import com.meongcare.domain.dog.presentation.dto.response.GetDogResponse;
import com.meongcare.domain.dog.presentation.dto.response.GetDogsResponse;
import com.meongcare.domain.supplements.domain.repository.SupplementsRecordQueryRepository;
import com.meongcare.domain.symptom.domain.repository.SymptomQueryRepository;
import com.meongcare.domain.symptom.domain.repository.vo.GetSymptomVO;
import com.meongcare.domain.weight.domain.entity.Weight;
import com.meongcare.domain.weight.domain.repository.WeightQueryRepository;
import com.meongcare.domain.weight.domain.repository.WeightRepository;
import com.meongcare.infra.image.ImageDirectory;
import com.meongcare.infra.image.ImageHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DogService {

    private final MemberRepository memberRepository;
    private final DogRepository dogRepository;
    private final WeightRepository weightRepository;
    private final SymptomQueryRepository symptomQueryRepository;
    private final WeightQueryRepository weightQueryRepository;
    private final ExcretaQueryRepository excretaQueryRepository;
    private final FeedRecordQueryRepository feedRecordQueryRepository;
    private final SupplementsRecordQueryRepository supplementsRecordQueryRepository;
    private final ImageHandler imageHandler;

    @Transactional
    public void saveDog(MultipartFile multipartFile, SaveDogRequest saveDogRequest, Long userId) {
        Member member = memberRepository.getById(userId);
        String dogImageURL = imageHandler.uploadImage(multipartFile, ImageDirectory.DOG);

        Dog dog = saveDogRequest.toEntity(member, dogImageURL);
        dogRepository.save(dog);

        Weight weight = Weight.createWeight(dog.getWeight(), userId);
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

    public GetAllRecordOfDogResponse getDogRecord(Long dogId, LocalDateTime dateTime) {
        List<GetSymptomVO> symptomVO = symptomQueryRepository.getSymptomByDogIdAndSelectedDate(
                dogId,
                LocalDateTimeUtils.createNowMidnight(dateTime),
                LocalDateTimeUtils.createNextMidnight(dateTime)
        );
        List<GetExcretaVO> excretaVO = excretaQueryRepository.getByDogIdAndSelectedDate(
                dogId,
                LocalDateTimeUtils.createNowMidnight(dateTime),
                LocalDateTimeUtils.createNextMidnight(dateTime)
        );
        double weight = weightQueryRepository.getDayWeightByDogIdAndDateTime(
                dogId,
                LocalDateTimeUtils.createNowMidnight(dateTime),
                LocalDateTimeUtils.createNextMidnight(dateTime)
        );
        Integer recommendIntake = feedRecordQueryRepository.getFeedRecordByDogIdAndDate(dogId, dateTime);

        int totalRecordCount = supplementsRecordQueryRepository.getTotalRecordCount(dogId, dateTime.toLocalDate());
        int intakeRecordCount = supplementsRecordQueryRepository.getIntakeRecordCount(dogId, dateTime.toLocalDate());
        return GetAllRecordOfDogResponse.of(
                symptomVO, excretaVO, weight, recommendIntake, intakeRecordCount, totalRecordCount
        );
    }
}
