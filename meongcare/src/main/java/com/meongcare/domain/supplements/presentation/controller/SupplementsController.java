package com.meongcare.domain.supplements.presentation.controller;

import com.meongcare.domain.supplements.application.SupplementsService;
import com.meongcare.domain.supplements.presentation.dto.request.SaveSupplementsRequest;
import com.meongcare.domain.supplements.presentation.dto.response.GetSupplementsRateResponse;
import com.meongcare.domain.supplements.presentation.dto.response.GetSupplementsResponse;
import com.meongcare.domain.supplements.presentation.dto.response.GetSupplementsRoutineResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.LocalTime;

import static com.meongcare.common.DateTimePattern.DATE_PATTERN;
import static com.meongcare.common.DateTimePattern.TIME_PATTERN;

@Tag(name = "영양제 API")
@RestController
@RequestMapping("/supplements")
@RequiredArgsConstructor
public class SupplementsController {

    private final SupplementsService supplementsService;

    @Operation(description = "영양제 등록")
    @Parameter(name = "AccessToken", in = ParameterIn.HEADER, required = true)
    @PostMapping
    public ResponseEntity<Void> saveSupplements(
            @RequestPart(value = "dto") @Valid SaveSupplementsRequest saveSupplementsRequest,
            @RequestPart(value = "file") MultipartFile multipartFile) {
        supplementsService.saveSupplements(saveSupplementsRequest, multipartFile);
        return ResponseEntity.ok().build();
    }

    @Operation(description = "날짜 별 영양제 루틴 조회")
    @Parameter(name = "AccessToken", in = ParameterIn.HEADER, required = true)
    @GetMapping
    public ResponseEntity<GetSupplementsRoutineResponse> getSupplements(
            @RequestParam("date") @DateTimeFormat(pattern = DATE_PATTERN) LocalDate date,
            @RequestParam("dogId") Long dogId
    ) {
        GetSupplementsRoutineResponse getSupplementsRoutineResponse = supplementsService.getSupplementsRoutine(date, dogId);
        return ResponseEntity.ok().body(getSupplementsRoutineResponse);
    }

    @Operation(description = "영양제 상세 조회")
    @Parameter(name = "AccessToken", in = ParameterIn.HEADER, required = true)
    @GetMapping("/{supplementsId}")
    public ResponseEntity<GetSupplementsResponse> getSupplementsInfo(
            @PathVariable Long supplementsId
    ) {
        GetSupplementsResponse getSupplementsResponse = supplementsService.getSupplementsInfo(supplementsId);
        return ResponseEntity.ok().body(getSupplementsResponse);
    }

    @Operation(description = "섭취 완료율 조회")
    @Parameter(name = "AccessToken", in = ParameterIn.HEADER, required = true)
    @GetMapping("/rate")
    public ResponseEntity<GetSupplementsRateResponse> getSupplementsRate(
            @RequestParam("date") @DateTimeFormat(pattern = DATE_PATTERN) LocalDate date,
            @RequestParam("dogId") Long dogId
    ) {
        GetSupplementsRateResponse getSupplementsRateResponse = supplementsService.getSupplementsRate(date, dogId);
        return ResponseEntity.ok().body(getSupplementsRateResponse);
    }

    @Operation(description = "섭취 여부 체크")
    @Parameter(name = "AccessToken", in = ParameterIn.HEADER, required = true)
    @PatchMapping("/check")
    public ResponseEntity<Void> updateSupplementsIntakeStatus(
            @RequestParam("supplementsRecordId") Long supplementsRecordId
    ) {
        supplementsService.updateSupplementsIntakeStatus(supplementsRecordId);
        return ResponseEntity.ok().build();
    }

    @Operation(description = "섭취 시간 수정")
    @Parameter(name = "AccessToken", in = ParameterIn.HEADER, required = true)
    @PatchMapping("/time")
    public ResponseEntity<Void> updateSupplementsTime(
            @RequestParam("supplementsTimeId") Long supplementsTimeId,
            @RequestParam("intakeTime") @DateTimeFormat(pattern = TIME_PATTERN) LocalTime updateIntakeTime
    ) {
        supplementsService.updateSupplementsTime(supplementsTimeId,updateIntakeTime);
        return ResponseEntity.ok().build();
    }
}
