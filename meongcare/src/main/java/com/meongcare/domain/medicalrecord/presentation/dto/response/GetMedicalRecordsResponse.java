package com.meongcare.domain.medicalrecord.presentation.dto.response;

import com.meongcare.domain.medicalrecord.domain.repository.vo.GetMedicalRecordsVo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Getter
public class GetMedicalRecordsResponse {

    private List<Record> records;

    @AllArgsConstructor
    @Getter
    static class Record {
        @Schema(description = "진료기록 ID", example = "1")
        private Long medicalRecordId;
        @Schema(description = "진료기록 시간", example = "2023-11-12'T'08:00:00")
        private LocalDateTime dateTime;
    }

    public static GetMedicalRecordsResponse of(List<GetMedicalRecordsVo> getMedicalRecordsVos) {
        List<Record> records = getMedicalRecordsVos.stream()
                .map(medicalRecord -> new Record(
                        medicalRecord.getMedicalRecordsId(),
                        medicalRecord.getDateTime()
                ))
                .collect(Collectors.toUnmodifiableList());

        return new GetMedicalRecordsResponse(records);
    }
}
