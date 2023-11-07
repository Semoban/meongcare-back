package com.meongcare.domain.weight.presentation;

import com.meongcare.domain.weight.application.WeightService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

import static com.meongcare.common.DateTimePattern.COMMON_PATTERN;

@RequiredArgsConstructor
@RequestMapping("/weight")
@RestController
public class WeightController {

    private final WeightService weightService;

    @GetMapping("/week/{dogId}")
    public ResponseEntity<?> getWeekWeight(
            @PathVariable Long dogId,
            @DateTimeFormat(pattern = COMMON_PATTERN) LocalDateTime dateTime
    ) {
        return ResponseEntity.ok(weightService.getWeekWeight(dogId, dateTime));
    }

    @GetMapping("/month/{dogId}")
    public ResponseEntity<?> getMonthWeight(
            @PathVariable Long dogId,
            @DateTimeFormat(pattern = COMMON_PATTERN) LocalDateTime dateTime
    ) {
        return ResponseEntity.ok(weightService.getMonthWeight(dogId, dateTime));
    }

    @PatchMapping("/{dogId}")
    public ResponseEntity<Void> updateWeight(@PathVariable Long dogId, @RequestParam double weight) {
        weightService.updateWeight(dogId, weight);
        return ResponseEntity.ok().build();
    }
}
