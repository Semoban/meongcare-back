package com.meongcare.domain.medicalrecord.presentation.dto.response;

import com.meongcare.domain.medicalrecord.domain.entity.MedicalRecord;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class GetMedicalRecordResponseDto {

    private Long medicalRecordId;

    private LocalDateTime dateTime;

    private String hospitalName;

    private String doctorName;

    private String note;

    private String imageUrl;

    @Builder
    public GetMedicalRecordResponseDto(Long medicalRecordId, LocalDateTime dateTime, String hospitalName, String doctorName, String note, String imageUrl) {
        this.medicalRecordId = medicalRecordId;
        this.dateTime = dateTime;
        this.hospitalName = hospitalName;
        this.doctorName = doctorName;
        this.note = note;
        this.imageUrl = imageUrl;
    }

    public static GetMedicalRecordResponseDto of(MedicalRecord medicalRecord) {
        return GetMedicalRecordResponseDto
                .builder()
                .medicalRecordId(medicalRecord.getId())
                .dateTime(medicalRecord.getDateTime())
                .hospitalName(medicalRecord.getHospitalName())
                .doctorName(medicalRecord.getDoctorName())
                .note(medicalRecord.getNote())
                .imageUrl(medicalRecord.getImageUrl())
                .build();
    }
}
