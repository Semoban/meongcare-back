package com.meongcare.domain.weight.domain.repository.vo;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class GetWeekWeightVO {
    private double weight;
    private LocalDateTime dateTime;

    @QueryProjection
    public GetWeekWeightVO(double weight, LocalDateTime dateTime) {
        this.weight = weight;
        this.dateTime = dateTime;
    }
}
