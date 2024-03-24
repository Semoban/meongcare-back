package com.meongcare.domain.medicalrecord.presentation;

import com.meongcare.domain.medicalrecord.application.MedicalRecordService;
import com.meongcare.domain.medicalrecord.presentation.dto.request.PutMedicalRecordRequest;
import com.meongcare.domain.medicalrecord.presentation.dto.request.SaveMedicalRecordRequest;
import com.meongcare.domain.medicalrecord.presentation.dto.response.GetMedicalRecordResponse;
import com.meongcare.domain.medicalrecord.presentation.dto.response.GetMedicalRecordsResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

import static com.meongcare.common.DateTimePattern.COMMON_PATTERN;

@Tag(name = "진료기록 API")
@RequiredArgsConstructor
@RequestMapping("/medical-record")
@RestController
public class MedicalRecordController {

    private final MedicalRecordService medicalRecordService;

    @Operation(description = "진료기록 저장")
    @Parameter(name = "AccessToken", in = ParameterIn.HEADER, required = true)
    @PostMapping
    public ResponseEntity<Void> saveMedicalRecord(@RequestBody @Valid SaveMedicalRecordRequest request) {
        medicalRecordService.save(request);
        return ResponseEntity.ok().build();
    }

    @Operation(description = "진료기록 편집")
    @Parameter(name = "AccessToken", in = ParameterIn.HEADER, required = true)
    @PutMapping
    public ResponseEntity<Void> updateMedicalRecord(@RequestBody @Valid PutMedicalRecordRequest request) {
        medicalRecordService.update(request);
        return ResponseEntity.ok().build();
    }

    @Operation(description = "진료기록 삭제")
    @Parameter(name = "AccessToken", in = ParameterIn.HEADER, required = true)
    @DeleteMapping
    public ResponseEntity<Void> deleteMedicalRecord(@RequestParam List<Long> medicalRecordIds) {
        medicalRecordService.deleteMedicalRecords(medicalRecordIds);
        return ResponseEntity.ok().build();
    }

    @Operation(description = "진료기록 상세 조회")
    @Parameter(name = "AccessToken", in = ParameterIn.HEADER, required = true)
    @GetMapping("/{medicalRecordId}")
    public ResponseEntity<GetMedicalRecordResponse> getMedicalRecord(@PathVariable Long medicalRecordId) {
        GetMedicalRecordResponse getMedicalRecordResponse = medicalRecordService.get(medicalRecordId);
        return ResponseEntity.ok().body(getMedicalRecordResponse);
    }

    @Operation(description = "진료기록 조회")
    @Parameter(name = "AccessToken", in = ParameterIn.HEADER, required = true)
    @GetMapping
    public ResponseEntity<GetMedicalRecordsResponse> getMedicalRecords(
            @RequestParam("dogId") Long dogId,
            @RequestParam("dateTime") @DateTimeFormat(pattern = COMMON_PATTERN) LocalDateTime dateTime) {
        GetMedicalRecordsResponse getMedicalRecordsResponse = medicalRecordService.getMedicalRecords(dogId, dateTime);
        return ResponseEntity.ok().body(getMedicalRecordsResponse);
    }

}
