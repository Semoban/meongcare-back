package com.meongcare.domain.medicalrecord.presentation.dto.response;

import com.meongcare.domain.medicalrecord.domain.entity.MedicalRecord;
import com.meongcare.domain.medicalrecord.domain.repository.vo.GetMedicalRecordsVo;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static com.meongcare.common.util.LocalDateTimeUtils.createAMPMTime;

@AllArgsConstructor
@Getter
public class GetMedicalRecordsResponseDto {

    private List<Record> records;

    @AllArgsConstructor
    @Getter
    static class Record {
        private Long medicalRecordId;
        private String time;
    }

    public static GetMedicalRecordsResponseDto of(List<GetMedicalRecordsVo> getMedicalRecordsVos) {
        List<Record> records = getMedicalRecordsVos.stream()
                .map(medicalRecord -> new Record(
                        medicalRecord.getMedicalRecordsId(),
                        createAMPMTime(medicalRecord.getDateTime())
                ))
                .collect(Collectors.toUnmodifiableList());

        return new GetMedicalRecordsResponseDto(records);
    }
}
