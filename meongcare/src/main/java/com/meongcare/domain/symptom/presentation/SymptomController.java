package com.meongcare.domain.symptom.presentation;

import com.meongcare.domain.symptom.application.SymptomService;
import com.meongcare.domain.symptom.presentation.dto.request.EditSymptomRequest;
import com.meongcare.domain.symptom.presentation.dto.request.SaveSymptomRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

import static com.meongcare.common.DateTimePattern.COMMON_PATTERN;

@RequiredArgsConstructor
@RequestMapping("/symptom")
@RestController
public class SymptomController {

    private final SymptomService symptomService;

    @PostMapping
    public ResponseEntity<?> saveSymptom(SaveSymptomRequest request) {
        symptomService.saveSymptom(request);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{dogId}")
    public ResponseEntity<?> getSymptom(
            @PathVariable Long dogId,
            @RequestParam @DateTimeFormat(pattern = COMMON_PATTERN) LocalDateTime dateTime) {
        return ResponseEntity.ok(symptomService.getSymptom(dogId, dateTime));
    }

    @PatchMapping
    public ResponseEntity<?> editSymptom(@RequestBody @Valid EditSymptomRequest request) {
        symptomService.editSymptom(request);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteSymptom(@RequestParam List<Long> symptomIds) {
        symptomService.deleteSymptom(symptomIds);
        return ResponseEntity.ok().build();
    }
}
