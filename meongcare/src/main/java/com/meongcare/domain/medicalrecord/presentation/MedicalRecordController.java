package com.meongcare.domain.medicalrecord.presentation;

import com.meongcare.domain.medicalrecord.application.MedicalRecordService;
import com.meongcare.domain.medicalrecord.presentation.dto.request.PutMedicalRecordRequestDto;
import com.meongcare.domain.medicalrecord.presentation.dto.request.SaveMedicalRecordRequestDto;
import com.meongcare.domain.medicalrecord.presentation.dto.response.GetMedicalRecordResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/medical-record")
@RestController
public class MedicalRecordController {

    private final MedicalRecordService medicalRecordService;

    @PostMapping
    public ResponseEntity<Void> saveMedicalRecord(
            @RequestPart(value = "file") MultipartFile multipartFile,
            @RequestPart(value = "dto") @Valid SaveMedicalRecordRequestDto saveMedicalRecordRequestDto) {
        medicalRecordService.save(multipartFile, saveMedicalRecordRequestDto);
        return ResponseEntity.ok().build();
    }

    @PutMapping
    public ResponseEntity<Void> updateMedicalRecord(
            @RequestPart(value = "file") MultipartFile multipartFile,
            @RequestPart(value = "dto") @Valid PutMedicalRecordRequestDto putMedicalRecordRequestDto) {
        medicalRecordService.update(multipartFile, putMedicalRecordRequestDto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteMedicalRecord(@RequestParam List<Long> medicalRecordIds) {
        medicalRecordService.deleteMedicalRecords(medicalRecordIds);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{medicalRecordId}")
    public ResponseEntity<GetMedicalRecordResponseDto> getMedicalRecord(@PathVariable Long medicalRecordId) {
        GetMedicalRecordResponseDto getMedicalRecordResponseDto = medicalRecordService.get(medicalRecordId);
        return ResponseEntity.ok().body(getMedicalRecordResponseDto);

    }

}
