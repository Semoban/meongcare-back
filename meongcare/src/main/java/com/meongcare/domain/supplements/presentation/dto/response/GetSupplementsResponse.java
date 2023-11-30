package com.meongcare.domain.supplements.presentation.dto.response;

import com.meongcare.domain.supplements.domain.entity.Supplements;
import com.meongcare.domain.supplements.domain.entity.SupplementsTime;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class GetSupplementsResponse {

    @Schema(description = "영양제 ID", example = "1")
    private Long supplementsId;

    @Schema(description = "영양제 브랜드명", example = "하림")
    private String brand;

    @Schema(description = "영양제 제품명", example = "밥이보약 하루양갱")
    private String name;

    @Schema(description = "섭취 단위", example = "포")
    private String intakeUnit;

    @Schema(description = "섭취 주기", example = "3일 마다")
    private int intakeCycle;

    List<IntakeInfo> intakeInfos;

    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @Getter
    static class IntakeInfo{
        @Schema(description = "섭취 시간", example = "13:00:00")
        private LocalTime intakeTime;

        @Schema(description = "섭취량", example = "3")
        private int intakeCount;
    }

    public static GetSupplementsResponse of(Supplements supplements, List<SupplementsTime> supplementsTimes) {
        List<IntakeInfo> intakeInfos = supplementsTimes.stream()
                .map(supplementsTime -> new IntakeInfo(
                        supplementsTime.getIntakeTime(),
                        supplementsTime.getIntakeCount()
                ))
                .collect(Collectors.toUnmodifiableList());

        return GetSupplementsResponse.builder()
                .supplementsId(supplements.getId())
                .brand(supplements.getBrand())
                .name(supplements.getName())
                .intakeUnit(supplements.getIntakeUnit())
                .intakeCycle(supplements.getIntakeCycle())
                .intakeInfos(intakeInfos)
                .build();
    }

}
