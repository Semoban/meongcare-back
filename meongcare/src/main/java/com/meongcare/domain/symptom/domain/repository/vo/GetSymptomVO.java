package com.meongcare.domain.symptom.domain.repository.vo;

import com.meongcare.domain.symptom.domain.entity.SymptomType;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class GetSymptomVO {
    private Long symptomId;
    private LocalDateTime dateTime;
    private SymptomType symptomType;
    private String note;

    @QueryProjection
    public GetSymptomVO(Long symptomId, LocalDateTime dateTime, SymptomType symptomType, String note) {
        this.symptomId = symptomId;
        this.dateTime = dateTime;
        this.symptomType = symptomType;
        this.note = note;
    }
}
