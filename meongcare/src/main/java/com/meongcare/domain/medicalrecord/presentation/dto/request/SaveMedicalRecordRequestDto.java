package com.meongcare.domain.medicalrecord.presentation.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.meongcare.domain.dog.domain.entity.Dog;
import com.meongcare.domain.medicalrecord.domain.entity.MedicalRecord;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

import static com.meongcare.common.DateTimePattern.COMMON_PATTERN;

@Getter
public class SaveMedicalRecordRequestDto {

    @NotNull
    private Long dogId;

    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = COMMON_PATTERN, timezone = "Asia/Seoul")
    private LocalDateTime dateTime;

    private String hospitalName;

    private String doctorName;

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
