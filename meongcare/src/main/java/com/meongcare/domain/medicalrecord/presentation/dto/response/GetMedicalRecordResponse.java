package com.meongcare.domain.medicalrecord.presentation.dto.response;

import com.meongcare.domain.medicalrecord.domain.entity.MedicalRecord;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class GetMedicalRecordResponse {

    @Schema(description = "진료기록 ID", example = "1")
    private Long medicalRecordId;

    @Schema(description = "진료 시간")
    private LocalDateTime dateTime;

    @Schema(description = "진료 병원", example = "XX병원")
    private String hospitalName;

    @Schema(description = "진료의", example = "홍길동")
    private String doctorName;

    @Schema(description = "진료 기록 메모", example = "운동을 많이 시킬것을 권함")
    private String note;

    @Schema(description = "진료 기록 사진", example = "https:://www.~~~")
    private String imageUrl;

    @Builder
    public GetMedicalRecordResponse(Long medicalRecordId, LocalDateTime dateTime, String hospitalName, String doctorName, String note, String imageUrl) {
        this.medicalRecordId = medicalRecordId;
        this.dateTime = dateTime;
        this.hospitalName = hospitalName;
        this.doctorName = doctorName;
        this.note = note;
        this.imageUrl = imageUrl;
    }

    public static GetMedicalRecordResponse of(MedicalRecord medicalRecord) {
        return GetMedicalRecordResponse
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
