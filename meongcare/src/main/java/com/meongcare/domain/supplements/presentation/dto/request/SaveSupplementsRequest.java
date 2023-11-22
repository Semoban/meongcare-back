package com.meongcare.domain.supplements.presentation.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.meongcare.domain.dog.domain.entity.Dog;
import com.meongcare.domain.supplements.domain.entity.Supplements;
import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Getter;


import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static com.meongcare.common.DateTimePattern.*;

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

    @Schema(description = "섭취량", example = "3")
    private int intakeCount;

    @Schema(description = "섭취 단위", example = "포")
    private String intakeUnit;

    @Schema(description = "섭취 시작 일자", example = "2023-10-17")
    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_PATTERN, timezone = "Asia/Seoul")
    private LocalDate startDate;

    @Schema(description = "섭취 종료 일자", example = "2023-10-26")
    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_PATTERN, timezone = "Asia/Seoul")
    private LocalDate endDate;

    @Schema(description = "섭취 시간 리스트", example = "[\"13:00:00\", \"18:00:00\"]")
    @NotNull
    private List<LocalTime> intakeTimes;

    public Supplements toEntity(Dog dog, String imageUrl) {
        return Supplements.builder()
                .dog(dog)
                .brand(brand)
                .name(name)
                .imageUrl(imageUrl)
                .intakeCycle(intakeCycle)
                .intakeCount(intakeCount)
                .intakeUnit(intakeUnit)
                .startDate(startDate)
                .endDate(endDate)
                .build();

    }
}
