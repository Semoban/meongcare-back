package com.meongcare.domain.supplements.domain.repository.vo;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

@Getter
public class GetAlarmSupplementsVO {
    private Long dogId;
    private String dogName;
    private String supplementsName;


    @QueryProjection
    public GetAlarmSupplementsVO(Long dogId, String dogName, String supplementsName) {
        this.dogId = dogId;
        this.dogName = dogName;
        this.supplementsName = supplementsName;
    }
}
