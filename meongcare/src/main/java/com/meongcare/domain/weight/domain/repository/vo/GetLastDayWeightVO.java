package com.meongcare.domain.weight.domain.repository.vo;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class GetLastDayWeightVO {

    private LocalDateTime dateTime;
    private double kg;

    @QueryProjection
    public GetLastDayWeightVO(LocalDateTime dateTime, double kg) {
        this.dateTime = dateTime;
        this.kg = kg;
    }
}
