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

    @Schema(description = "이상증상 타입 문자열", example = "weightLoss")
    private List<String> symptoms;

    public static GetSymptomForHomeResponse from(List<GetSymptomVO> symptomVO) {
        List<String> symptoms = symptomVO.stream()
                .map(vo -> vo.getSymptomType().getSymptomString())
                .collect(Collectors.toList());

        return new GetSymptomForHomeResponse(symptoms);
    }
}
