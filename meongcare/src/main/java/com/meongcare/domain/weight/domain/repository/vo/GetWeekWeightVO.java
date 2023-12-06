package com.meongcare.domain.weight.domain.repository.vo;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class GetWeekWeightVO {
    private double weight;
    private LocalDate date;

    @QueryProjection
    public GetWeekWeightVO(double weight, LocalDate date) {
        this.weight = weight;
        this.date = date;
    }
}
