package com.meongcare.domain.excreta.domain.repository.vo;

import com.meongcare.domain.excreta.domain.entity.ExcretaType;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class GetExcretaVO {
    private Long excretaId;
    private LocalDateTime dateTime;
    private ExcretaType excretaType;

    @QueryProjection
    public GetExcretaVO(Long excretaId, LocalDateTime dateTime, ExcretaType excretaType) {
        this.excretaId = excretaId;
        this.dateTime = dateTime;
        this.excretaType = excretaType;
    }
}
