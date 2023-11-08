package com.meongcare.domain.medicalrecord.presentation;

import com.meongcare.domain.medicalrecord.application.MedicalRecordService;
import com.meongcare.domain.medicalrecord.presentation.dto.request.PutMedicalRecordRequestDto;
import com.meongcare.domain.medicalrecord.presentation.dto.request.SaveMedicalRecordRequestDto;
import com.meongcare.domain.medicalrecord.presentation.dto.response.GetMedicalRecordResponseDto;
import com.meongcare.domain.medicalrecord.presentation.dto.response.GetMedicalRecordsResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
    @PostMapping
    public ResponseEntity<Void> saveMedicalRecord(
            @RequestPart(value = "file") MultipartFile multipartFile,
            @RequestPart(value = "dto") @Valid SaveMedicalRecordRequestDto saveMedicalRecordRequestDto) {
        medicalRecordService.save(multipartFile, saveMedicalRecordRequestDto);
        return ResponseEntity.ok().build();
    }

    @Operation(description = "진료기록 편집")
    @PutMapping
    public ResponseEntity<Void> updateMedicalRecord(
            @RequestPart(value = "file") MultipartFile multipartFile,
            @RequestPart(value = "dto") @Valid PutMedicalRecordRequestDto putMedicalRecordRequestDto) {
        medicalRecordService.update(multipartFile, putMedicalRecordRequestDto);
        return ResponseEntity.ok().build();
    }

    @Operation(description = "진료기록 삭제")
    @DeleteMapping
    public ResponseEntity<Void> deleteMedicalRecord(@RequestParam List<Long> medicalRecordIds) {
        medicalRecordService.deleteMedicalRecords(medicalRecordIds);
        return ResponseEntity.ok().build();
    }

    @Operation(description = "진료기록 상세 조회")
    @GetMapping("/{medicalRecordId}")
    public ResponseEntity<GetMedicalRecordResponseDto> getMedicalRecord(@PathVariable Long medicalRecordId) {
        GetMedicalRecordResponseDto getMedicalRecordResponseDto = medicalRecordService.get(medicalRecordId);
        return ResponseEntity.ok().body(getMedicalRecordResponseDto);
    }

    @Operation(description = "진료기록 조회")
    @GetMapping
    public ResponseEntity<GetMedicalRecordsResponseDto> getMedicalRecords(
            @RequestParam("dogId") Long dogId,
            @RequestParam("dateTime") @DateTimeFormat(pattern = COMMON_PATTERN) LocalDateTime dateTime) {
        GetMedicalRecordsResponseDto getMedicalRecordsResponseDto = medicalRecordService.getMedicalRecords(dogId, dateTime);
        return ResponseEntity.ok().body(getMedicalRecordsResponseDto);
    }

}
