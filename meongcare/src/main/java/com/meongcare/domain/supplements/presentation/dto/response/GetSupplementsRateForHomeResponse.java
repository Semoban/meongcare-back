package com.meongcare.domain.supplements.presentation.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class GetSupplementsRateForHomeResponse {

    @Schema(description = "영양제 섭취 완료율", example = "13")
    private int supplementsRate;

    public static GetSupplementsRateForHomeResponse of(int isIntakeRecordCount, int totalRecordCount) {
        return new GetSupplementsRateForHomeResponse(calSupplementsRate(isIntakeRecordCount, totalRecordCount));
    }

    private static int calSupplementsRate(int isIntakeRecordCount, int totalRecordCount){
        if (totalRecordCount == 0){
            return 0;
        }
        return (isIntakeRecordCount * 100) / totalRecordCount;
    }
}
