package com.meongcare.domain.supplements.presentation.controller;

import com.meongcare.domain.supplements.application.SupplementsService;
import com.meongcare.domain.supplements.presentation.dto.request.SaveSupplementsRequest;
import com.meongcare.domain.supplements.presentation.dto.response.*;
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
import java.util.List;

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

    @Operation(description = "강아지 ID 영양제 조회")
    @Parameter(name = "AccessToken", in = ParameterIn.HEADER, required = true)
    @GetMapping("/dog/{dogId}")
    public ResponseEntity<GetSupplementsResponse> getSupplementsRateForHome(
            @PathVariable Long dogId
    ) {
        GetSupplementsResponse getSupplementsResponse = supplementsService.getSupplements(dogId);
        return ResponseEntity.ok().body(getSupplementsResponse);
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
    public ResponseEntity<GetSupplementsInfoResponse> getSupplementsInfo(
            @PathVariable Long supplementsId
    ) {
        GetSupplementsInfoResponse getSupplementsInfoResponse = supplementsService.getSupplementsInfo(supplementsId);
        return ResponseEntity.ok().body(getSupplementsInfoResponse);
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

    @Operation(description = "루틴 활성화 체크")
    @Parameter(name = "AccessToken", in = ParameterIn.HEADER, required = true)
    @PatchMapping("/active")
    public ResponseEntity<Void> activeSupplementsRoutine(
            @RequestParam("supplementsId") Long supplementsId,
            @RequestParam("isActive") boolean isActive
    ) {
        supplementsService.stopSupplementsRoutine(supplementsId, isActive);
        return ResponseEntity.ok().build();
    }

    @Operation(description = "푸시 알람 체크")
    @Parameter(name = "AccessToken", in = ParameterIn.HEADER, required = true)
    @PatchMapping("/alarm")
    public ResponseEntity<Void> activePushAgreement(
            @RequestParam("supplementsId") Long supplementsId,
            @RequestParam("pushAgreement") boolean pushAgreement
    ) {
        supplementsService.updatePushAgreement(supplementsId, pushAgreement);
        return ResponseEntity.ok().build();
    }

    @Operation(description = "영양제 삭제")
    @Parameter(name = "AccessToken", in = ParameterIn.HEADER, required = true)
    @DeleteMapping("/{supplementsId}")
    public ResponseEntity<Void> deleteSupplements(
            @PathVariable Long supplementsId
    ) {
        supplementsService.deleteSupplements(supplementsId);
        return ResponseEntity.ok().build();
    }

    @Operation(description = "영양제 삭제")
    @Parameter(name = "AccessToken", in = ParameterIn.HEADER, required = true)
    @DeleteMapping
    public ResponseEntity<Void> deleteSupplements(
            @RequestParam("supplementsIds") List<Long> supplementsIds
    ) {
        supplementsService.deleteSupplements(supplementsIds);
        return ResponseEntity.ok().build();
    }

    @Operation(description = "반려견 메인 홈 영양제 섭취율 조회")
    @Parameter(name = "AccessToken", in = ParameterIn.HEADER, required = true)
    @GetMapping("/home/{dogId}")
    public ResponseEntity<GetSupplementsRateForHomeResponse> getSupplementsRateForHome(
            @PathVariable Long dogId,
            @RequestParam @DateTimeFormat(pattern = DATE_PATTERN) LocalDate date
    ) {
        return ResponseEntity.ok(supplementsService.getSupplementsRateForHome(dogId, date));
    }
}
