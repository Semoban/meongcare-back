package com.meongcare.domain.supplements.domain.repository.vo;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
public class GetSupplementsRoutineVO {
    private Long supplementsId;
    private String name;
    private LocalDate startDate;
    private int intakeCycle;
    private int intakeCount;
    private String intakeUnit;
    private LocalTime intakeTime;

    @QueryProjection
    public GetSupplementsRoutineVO(Long supplementsId, String name, LocalDate startDate, int intakeCycle, int intakeCount, String intakeUnit, LocalTime intakeTime) {
        this.supplementsId = supplementsId;
        this.name = name;
        this.startDate = startDate;
        this.intakeCycle = intakeCycle;
        this.intakeCount = intakeCount;
        this.intakeUnit = intakeUnit;
        this.intakeTime = intakeTime;
    }

}
