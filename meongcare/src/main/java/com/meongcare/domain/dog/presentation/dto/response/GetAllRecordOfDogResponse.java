package com.meongcare.domain.dog.presentation.dto.response;

import com.meongcare.domain.excreta.domain.entity.ExcretaType;
import com.meongcare.domain.excreta.domain.repository.vo.GetExcretaVO;
import com.meongcare.domain.symptom.domain.repository.vo.GetSymptomVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Getter
public class GetAllRecordOfDogResponse {

    @Schema(description = "이상증상 타입 문자열", example = "weightLoss")
    private List<String> symptoms;

    @Schema(description = "대변 횟수", example = "1")
    private int fecesCount;

    @Schema(description = "소변 횟수", example = "2")
    private int urineCount;

    @Schema(description = "영양제 섭취 완료율", example = "13")
    private int supplementsRate;

    @Schema(description = "몸무게", example = "5.6")
    private Double weight;

    @Schema(description = "사료 권장 섭취량", example = "30")
    private int recommendIntake;


    public static GetAllRecordOfDogResponse of(
            List<GetSymptomVO> symptomVO, List<GetExcretaVO> excretaVO, Double weight,
            Integer recommendIntake, int isIntakeRecordCount, int totalRecordCount
    ) {
        List<String> symptoms = symptomVO.stream()
                .map(vo -> vo.getSymptomType().getSymptomString())
                .collect(Collectors.toList());

        int fecesCount = getFecesCount(excretaVO);
        int urineCount = excretaVO.size() - fecesCount;

        return new GetAllRecordOfDogResponse(
                symptoms,
                fecesCount,
                urineCount,
                calSupplementsRate(isIntakeRecordCount, totalRecordCount),
                weight,
                recommendIntake
        );
    }

    private static int getFecesCount(List<GetExcretaVO> excretaVO) {
        return (int) excretaVO.stream()
                .filter(excreta -> excreta.getExcretaType() == ExcretaType.FECES)
                .count();
    }

    private static int calSupplementsRate(int isIntakeRecordCount, int totalRecordCount){
        if (totalRecordCount == 0){
            return 0;
        }
        return (isIntakeRecordCount * 100) / totalRecordCount;
    }
}
