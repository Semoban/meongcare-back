package com.meongcare.domain.excreta.presentation;

import com.meongcare.domain.excreta.application.ExcretaService;
import com.meongcare.domain.excreta.presentation.dto.request.SaveExcretaRequest;
import com.meongcare.domain.excreta.presentation.dto.request.PatchExcretaRequest;
import com.meongcare.domain.excreta.presentation.dto.response.GetExcretaDetailResponse;
import com.meongcare.domain.excreta.presentation.dto.response.GetExcretaResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

import static com.meongcare.common.DateTimePattern.COMMON_PATTERN;

@Tag(name = "대소변 API")
@RequiredArgsConstructor
@RequestMapping("/excreta")
@RestController
public class ExcretaController {

    private final ExcretaService excretaService;

    @Operation(description = "대소변 저장")
    @Parameter(name = "AccessToken", in = ParameterIn.HEADER, required = true)
    @PostMapping
    public ResponseEntity<Void> saveExcreta(
            @RequestPart(value = "dto") @Valid SaveExcretaRequest request,
            @RequestPart(value = "file") MultipartFile multipartFile
    ) {
        excretaService.saveExcreta(request, multipartFile);
        return ResponseEntity.ok().build();
    }

    @Operation(description = "대소변 조회")
    @Parameter(name = "AccessToken", in = ParameterIn.HEADER, required = true)
    @GetMapping("/{dogId}")
    public ResponseEntity<GetExcretaResponse> getExcreta(
            @PathVariable Long dogId,
            @RequestParam("dateTime") @DateTimeFormat(pattern = COMMON_PATTERN) LocalDateTime dateTime) {
        return ResponseEntity.ok(excretaService.getExcreta(dogId, dateTime));
    }

    @Operation(description = "대소변 수정")
    @Parameter(name = "AccessToken", in = ParameterIn.HEADER, required = true)
    @PatchMapping
    public ResponseEntity<Void> patchExcreta(
            @RequestPart(value = "dto") @Valid PatchExcretaRequest request,
            @RequestPart(value = "file") MultipartFile multipartFile
    ) {
        excretaService.editExcreta(request, multipartFile);
        return ResponseEntity.ok().build();
    }

    @Operation(description = "대소변 삭제")
    @Parameter(name = "AccessToken", in = ParameterIn.HEADER, required = true)
    @DeleteMapping
    public ResponseEntity<Void> deleteExcreta(@RequestParam List<Long> excretaIds) {
        excretaService.deleteExcreta(excretaIds);
        return ResponseEntity.ok().build();
    }

    @Operation(description = "대소변 이미지 조회")
    @Parameter(name = "AccessToken", in = ParameterIn.HEADER, required = true)
    @GetMapping("/image/{excretaId}")
    public ResponseEntity<String> getExcretaImage(@PathVariable Long excretaId) {
        return ResponseEntity.ok(excretaService.getExcretaImage(excretaId));
    }

    @Operation(description = "대소변 상세 조회")
    @Parameter(name = "AccessToken", in = ParameterIn.HEADER, required = true)
    @GetMapping("/detail/{excretaId}")
    public ResponseEntity<GetExcretaDetailResponse> getExcretaDetail(@PathVariable Long excretaId) {
        return ResponseEntity.ok(excretaService.getExcretaDetail(excretaId));
    }

    @Operation(description = "반려견 메인 홈 대소변 조회")
    @Parameter(name = "AccessToken", in = ParameterIn.HEADER, required = true)
    @GetMapping("/home/{dogId}")
    public ResponseEntity<?> getExcretaForHome(
            @PathVariable Long dogId,
            @RequestParam @DateTimeFormat(pattern = COMMON_PATTERN) LocalDateTime dateTime
    ) {
        return ResponseEntity.ok(excretaService.getExcretaForHome(dogId, dateTime));
    }
}
