package com.meongcare.domain.supplements.domain.repository.vo;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

import java.time.LocalTime;

@Getter
public class GetSupplementsRoutineWithoutStatusVO {
    private String name;
    private int intakeCount;
    private String intakeUnit;
    private LocalTime intakeTime;

    @QueryProjection
    public GetSupplementsRoutineWithoutStatusVO(String name, int intakeCount, String intakeUnit, LocalTime intakeTime) {
        this.name = name;
        this.intakeCount = intakeCount;
        this.intakeUnit = intakeUnit;
        this.intakeTime = intakeTime;
    }
}
