package com.meongcare.domain.weight.domain.repository.vo;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class GetLastDayWeightVO {

    private LocalDate date;
    private double kg;

    @QueryProjection
    public GetLastDayWeightVO(LocalDate date, double kg) {
        this.date = date;
        this.kg = kg;
    }
}
