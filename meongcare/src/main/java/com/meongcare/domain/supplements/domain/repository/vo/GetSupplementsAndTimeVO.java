package com.meongcare.domain.supplements.domain.repository.vo;

import com.meongcare.domain.supplements.domain.entity.Supplements;
import com.meongcare.domain.supplements.domain.entity.SupplementsTime;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

@Getter
public class GetSupplementsAndTimeVO {

    private Supplements supplements;
    private SupplementsTime supplementsTime;

    @QueryProjection
    public GetSupplementsAndTimeVO(Supplements supplements, SupplementsTime supplementsTime) {
        this.supplements = supplements;
        this.supplementsTime = supplementsTime;
    }

}
