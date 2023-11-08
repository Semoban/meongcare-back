package com.meongcare.domain.medicalrecord.presentation.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.meongcare.domain.dog.domain.entity.Dog;
import com.meongcare.domain.medicalrecord.domain.entity.MedicalRecord;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

import static com.meongcare.common.DateTimePattern.COMMON_PATTERN;

@Getter
public class SaveMedicalRecordRequestDto {

    @Schema(description = "강아지 ID", example = "1")
    @NotNull
    private Long dogId;

    @Schema(description = "진료 시간", example = "2023-01-05T11:00:00")
    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = COMMON_PATTERN, timezone = "Asia/Seoul")
    private LocalDateTime dateTime;

    @Schema(description = "진료 병원", example = "서울 병원")
    private String hospitalName;

    @Schema(description = "진료 의사 이름", example = "김땡떙")
    private String doctorName;

    @Schema(description = "진료 노트", example = "기침과 고열을 느낌")
    private String note;

    public MedicalRecord toEntity(Dog dog, String imageUrl) {
        return MedicalRecord
                .builder()
                .dog(dog)
                .dateTime(dateTime)
                .hospitalName(hospitalName)
                .doctorName(doctorName)
                .note(note)
                .imageUrl(imageUrl)
                .build();
    }
}
