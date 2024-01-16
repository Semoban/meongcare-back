package com.meongcare.domain.symptom.presentation.dto.response;

import com.meongcare.domain.symptom.domain.repository.vo.GetSymptomVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class GetSymptomResponse {

    List<Record> records;

    @Getter
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    static class Record {
        @Schema(description = "이상증상 ID", example = "1")
        private Long symptomId;
        @Schema(description = "이상증상 시간", example = "2023-10-27T08:13:22")
        private LocalDateTime dateTime;
        @Schema(description = "이상증상 문자열", example = "weightLoss")
        private String symptomString;
        @Schema(description = "이상증상 기타 시 기록", example = "많이 아파 보임")
        private String note;
    }

    public static GetSymptomResponse from(List<GetSymptomVO> symptomVO) {
        List<Record> records = symptomVO.stream()
                .map(symptom -> new Record(
                        symptom.getSymptomId(),
                        symptom.getDateTime(),
                        symptom.getSymptomType().getSymptomString(),
                        symptom.getNote()
                ))
                .collect(Collectors.toUnmodifiableList());
        return new GetSymptomResponse(records);
    }
}
