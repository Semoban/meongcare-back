package com.meongcare.domain.weight.domain.repository.vo;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

@Getter
public class GetMonthWeightVO {
    private double weight;
    private int month;

    @QueryProjection
    public GetMonthWeightVO(double weight, int month) {
        this.weight = weight;
        this.month = month;
    }
}
