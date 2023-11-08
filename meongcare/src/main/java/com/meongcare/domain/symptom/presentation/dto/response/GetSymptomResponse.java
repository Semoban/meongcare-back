package com.meongcare.domain.symptom.presentation.dto.response;

import com.meongcare.common.util.LocalDateTimeUtils;
import com.meongcare.domain.symptom.domain.entity.SymptomType;
import com.meongcare.domain.symptom.domain.repository.vo.GetSymptomVO;
import io.swagger.v3.oas.annotations.media.Schema;
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
        @Schema(description = "이상증상 ID", example = "1")
        private Long symptomId;
        @Schema(description = "이상증상 시간", example = "오전 08:00")
        private String dateTime;
        @Schema(description = "이상증상 문자열", example = "weightLoss")
        private String symptomString;
        @Schema(description = "이상증상 기타 시 기록", example = "많이 아파 보임")
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
