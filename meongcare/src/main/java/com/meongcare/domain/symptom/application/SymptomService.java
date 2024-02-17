package com.meongcare.domain.symptom.application;

import com.meongcare.common.util.LocalDateTimeUtils;
import com.meongcare.domain.dog.domain.DogRepository;
import com.meongcare.domain.dog.domain.entity.Dog;
import com.meongcare.domain.symptom.domain.entity.SymptomType;
import com.meongcare.domain.symptom.domain.repository.vo.GetSymptomVO;
import com.meongcare.domain.symptom.presentation.dto.request.EditSymptomRequest;
import com.meongcare.domain.symptom.domain.entity.Symptom;
import com.meongcare.domain.symptom.domain.repository.SymptomQueryRepository;
import com.meongcare.domain.symptom.domain.repository.SymptomRepository;
import com.meongcare.domain.symptom.presentation.dto.request.SaveSymptomRequest;
import com.meongcare.domain.symptom.presentation.dto.response.GetSymptomForHomeResponse;
import com.meongcare.domain.symptom.presentation.dto.response.GetSymptomResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static com.meongcare.common.util.LocalDateTimeUtils.createNextMidnight;
import static com.meongcare.common.util.LocalDateTimeUtils.createNowMidnight;

@RequiredArgsConstructor
@Transactional
@Service
public class SymptomService {

    private final DogRepository dogRepository;
    private final SymptomRepository symptomRepository;
    private final SymptomQueryRepository symptomQueryRepository;

    public void saveSymptom(SaveSymptomRequest request) {
        Dog dog = dogRepository.getDog(request.getDogId());
        symptomRepository.save(
                Symptom.of(
                        SymptomType.of(request.getSymptomString()),
                        request.getNote(),
                        request.getDateTime(),
                        dog
                )
        );
    }


    @Transactional(readOnly = true)
    public GetSymptomResponse getSymptom(Long dogId, LocalDateTime dateTime) {
        Dog dog = dogRepository.getDog(dogId);

        List<GetSymptomVO> symptomVO = symptomQueryRepository.getSymptomByDogIdAndSelectedDate(
                dog.getId(),
                createNowMidnight(dateTime),
                createNextMidnight(dateTime)
        );
        return GetSymptomResponse.from(symptomVO);
    }

    public void editSymptom(EditSymptomRequest request) {
        Symptom symptom = symptomRepository.getById(request.getSymptomId());
        symptom.updateRecord(
                SymptomType.of(request.getSymptomString()),
                request.getNote(),
                request.getDateTime()
        );
    }

    public void deleteSymptom(List<Long> symptomIds) {
        symptomQueryRepository.deleteSymptomById(symptomIds);
    }

    public GetSymptomForHomeResponse getSymptomForHome(Long dogId, LocalDateTime dateTime) {
        List<GetSymptomVO> symptomVO = symptomQueryRepository.getSymptomByDogIdAndSelectedDate(
                dogId,
                LocalDateTimeUtils.createNowMidnight(dateTime),
                LocalDateTimeUtils.createNextMidnight(dateTime)
        );
        return GetSymptomForHomeResponse.from(symptomVO);
    }
}
