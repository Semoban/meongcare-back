package com.meongcare.domain.medicalrecord.domain.repository.vo;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class GetMedicalRecordsVo {
    private Long medicalRecordsId;
    private LocalDateTime dateTime;

    @QueryProjection
    public GetMedicalRecordsVo(Long medicalRecordsId, LocalDateTime dateTime) {
        this.medicalRecordsId = medicalRecordsId;
        this.dateTime = dateTime;
    }
}
