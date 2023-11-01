package com.meongcare.domain.symptom.application;

import com.meongcare.domain.dog.domain.DogRepository;
import com.meongcare.domain.dog.domain.entity.Dog;
import com.meongcare.domain.symptom.domain.entity.SymptomType;
import com.meongcare.domain.symptom.domain.repository.vo.GetSymptomVO;
import com.meongcare.domain.symptom.presentation.dto.request.EditSymptomRequest;
import com.meongcare.domain.symptom.domain.entity.Symptom;
import com.meongcare.domain.symptom.domain.repository.SymptomQueryRepository;
import com.meongcare.domain.symptom.domain.repository.SymptomRepository;
import com.meongcare.domain.symptom.presentation.dto.request.SaveSymptomRequest;
import com.meongcare.domain.symptom.presentation.dto.response.GetSymptomResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static com.meongcare.common.util.LocalDateTimeUtils.createNextMidnight;
import static com.meongcare.common.util.LocalDateTimeUtils.createNowMidnight;

@RequiredArgsConstructor
@Service
public class SymptomService {

    private final DogRepository dogRepository;
    private final SymptomRepository symptomRepository;
    private final SymptomQueryRepository symptomQueryRepository;

    @Transactional
    public void saveSymptom(SaveSymptomRequest request) {
        Dog dog = dogRepository.getById(request.getDogId());
        symptomRepository.save(
                Symptom.of(
                        SymptomType.of(request.getSymptomString()),
                        request.getNote(),
                        request.getDateTime(),
                        dog.getId()
                )
        );
    }


    @Transactional(readOnly = true)
    public GetSymptomResponse getSymptom(Long dogId, LocalDateTime dateTime) {
        Dog dog = dogRepository.getById(dogId);

        List<GetSymptomVO> symptomVO = symptomQueryRepository.getSymptomByDogIdAndDatetime(
                dog.getId(),
                createNowMidnight(dateTime),
                createNextMidnight(dateTime)
        );
        return GetSymptomResponse.of(symptomVO);
    }

    @Transactional
    public void editSymptom(EditSymptomRequest request) {
        Symptom symptom = symptomRepository.getById(request.getSymptomId());
        symptom.updateRecord(
                SymptomType.of(request.getSymptomString()),
                request.getNote(),
                request.getDateTime()
        );
    }

    @Transactional
    public void deleteSymptom(List<Long> symptomIds) {
        symptomQueryRepository.deleteSymptomById(symptomIds);
    }
}
