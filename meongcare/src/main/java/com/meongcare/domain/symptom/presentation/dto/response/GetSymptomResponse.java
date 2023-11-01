package com.meongcare.domain.symptom.presentation.dto.response;

import com.meongcare.common.util.LocalDateTimeUtils;
import com.meongcare.domain.symptom.domain.entity.SymptomType;
import com.meongcare.domain.symptom.domain.repository.vo.GetSymptomVO;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Getter
public class GetSymptomResponse {

    List<Record> records;

    @AllArgsConstructor
    @Getter
    static class Record {
        private Long symptomId;
        private String dateTime;
        private String symptomString;
        private String note;
    }

    public static GetSymptomResponse from(List<GetSymptomVO> symptomVO) {
        List<Record> records = symptomVO.stream()
                .map(symptom -> new Record(
                        symptom.getSymptomId(),
                        LocalDateTimeUtils.createAMPMTime(symptom.getDateTime()),
                        symptom.getSymptomType().getSymptomString(),
                        symptom.getNote()
                ))
                .collect(Collectors.toUnmodifiableList());
        return new GetSymptomResponse(records);
    }
}
