package com.meongcare.domain.supplements.presentation.dto.request;

import com.meongcare.domain.dog.domain.entity.Dog;
import com.meongcare.domain.supplements.domain.entity.Supplements;
import com.meongcare.domain.supplements.domain.entity.SupplementsTime;
import io.swagger.v3.oas.annotations.media.Schema;

import lombok.AllArgsConstructor;
import lombok.Getter;


import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Getter
public class SaveSupplementsRequest {

    @Schema(description = "강아지 ID", example = "1")
    @NotNull
    private Long dogId;

    @Schema(description = "브랜드명", example = "하림")
    @NotNull
    private String brand;

    @Schema(description = "영양제 제품명", example = "밥이보약 하루양갱")
    @NotNull
    private String name;

    @Schema(description = "섭취 주기", example = "3")
    private int intakeCycle;

    @Schema(description = "섭취 단위", example = "포")
    private String intakeUnit;

    private List<IntakeInfo> intakeInfos;
    @AllArgsConstructor
    @Getter
    static class IntakeInfo{
        @Schema(description = "섭취 시간 리스트", example = "13:00:00")
        @NotNull
        private LocalTime intakeTime;
        @Schema(description = "섭취량", example = "3")
        private int intakeCount;
    }

    public Supplements toSupplements(Dog dog, String imageUrl) {
        return Supplements.builder()
                .dog(dog)
                .brand(brand)
                .name(name)
                .intakeUnit(intakeUnit)
                .startDate(LocalDate.now())
                .imageUrl(imageUrl)
                .intakeCycle(intakeCycle)
                .build();
    }

    public List<SupplementsTime> toSupplementsTimes(Supplements supplements){
        return intakeInfos.stream()
                .map(intakeInfo -> SupplementsTime.of(
                        supplements,
                        intakeInfo.intakeTime,
                        intakeInfo.intakeCount))
                .collect(Collectors.toUnmodifiableList());

    }

}
