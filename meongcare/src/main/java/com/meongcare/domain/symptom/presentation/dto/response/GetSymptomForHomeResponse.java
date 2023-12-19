package com.meongcare.domain.symptom.presentation.dto.response;

import com.meongcare.domain.symptom.domain.repository.vo.GetSymptomVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class GetSymptomForHomeResponse {

    @Schema(description = "이상증상 타입과 설명")
    private List<SymptomRecord> symptomRecords;

    @Getter
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    static class SymptomRecord {

        @Schema(description = "이상증상 타입 문자열", example = "weightLoss")
        private String symptomString;

        @Schema(description = "이상증상 노트 기록", example = "쓰러질 정도로 지쳐있음")
        private String note;

    }
    public static GetSymptomForHomeResponse from(List<GetSymptomVO> symptomVO) {
        List<SymptomRecord> symptomRecords = symptomVO.stream()
                .map(vo -> new SymptomRecord(
                        vo.getSymptomType().getSymptomString(),
                        vo.getNote()
                ))
                .collect(Collectors.toList());

        return new GetSymptomForHomeResponse(symptomRecords);
    }
}
