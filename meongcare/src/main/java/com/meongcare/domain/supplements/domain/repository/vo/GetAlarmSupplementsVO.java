package com.meongcare.domain.supplements.domain.repository.vo;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

@Getter
public class GetAlarmSupplementsVO {
    private String fcmToken;
    private String dogName;
    private String supplementsName;

    @QueryProjection
    public GetAlarmSupplementsVO(String fcmToken, String dogName, String supplementsName) {
        this.fcmToken = fcmToken;
        this.dogName = dogName;
        this.supplementsName = supplementsName;
    }
}
