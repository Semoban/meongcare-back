package com.meongcare.domain.supplements.domain.repository.vo;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

@Getter
public class GetAlarmSupplementsVO {
    private String fcmToken;
    private String dogName;
    private String supplementsName;
    private Long memberId;
    private Long dogId;

    @QueryProjection
    public GetAlarmSupplementsVO(String fcmToken, String dogName, String supplementsName, Long memberId, Long dogId) {
        this.fcmToken = fcmToken;
        this.dogName = dogName;
        this.supplementsName = supplementsName;
        this.memberId = memberId;
        this.dogId = dogId;
    }
}
