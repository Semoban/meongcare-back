package com.meongcare.domain.excreta.presentation;

import com.meongcare.domain.excreta.application.ExcretaService;
import com.meongcare.domain.excreta.presentation.dto.request.SaveExcretaRequest;
import com.meongcare.domain.excreta.presentation.dto.request.PatchExcretaRequest;
import com.meongcare.domain.excreta.presentation.dto.response.GetExcretaResponse;
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

@RequiredArgsConstructor
@RequestMapping("/excreta")
@RestController
public class ExcretaController {

    private final ExcretaService excretaService;

    @PostMapping
    public ResponseEntity<Void> saveExcreta(
            @RequestPart(value = "dto") @Valid SaveExcretaRequest request,
            @RequestPart(value = "file") MultipartFile multipartFile
    ) {
        excretaService.saveExcreta(request, multipartFile);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{dogId}")
    public ResponseEntity<GetExcretaResponse> getExcreta(
            @PathVariable Long dogId,
            @RequestParam("dateTime") @DateTimeFormat(pattern = COMMON_PATTERN) LocalDateTime dateTime) {
        return ResponseEntity.ok(excretaService.getExcreta(dogId, dateTime));
    }

    @PatchMapping
    public ResponseEntity<Void> patchExcreta(
            @RequestPart(value = "dto") @Valid PatchExcretaRequest request,
            @RequestPart(value = "file") MultipartFile multipartFile
    ) {
        excretaService.editExcreta(request, multipartFile);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteExcreta(@RequestParam List<Long> excretaIds) {
        excretaService.deleteExcreta(excretaIds);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/image/{excretaId}")
    public ResponseEntity<String> getExcretaImage(@PathVariable Long excretaId) {
        return ResponseEntity.ok(excretaService.getExcretaImage(excretaId));
    }
}
