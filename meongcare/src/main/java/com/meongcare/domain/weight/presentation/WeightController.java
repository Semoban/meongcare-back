package com.meongcare.domain.weight.presentation;

import com.meongcare.domain.weight.application.WeightService;
import com.meongcare.domain.weight.presentation.dto.request.SaveWeightRequest;
import com.meongcare.domain.weight.presentation.dto.response.GetDayWeightResponse;
import com.meongcare.domain.weight.presentation.dto.response.GetWeekWeightResponse;
import com.meongcare.domain.weight.presentation.dto.response.GetMonthWeightResponse;
import com.meongcare.domain.weight.presentation.dto.response.GetWeightForHomeResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.time.LocalDate;

import static com.meongcare.common.DateTimePattern.DATE_PATTERN;

@Tag(name = "몸무게 API")
@RequiredArgsConstructor
@RequestMapping("/weight")
@RestController
public class WeightController {

    private final WeightService weightService;

    @Operation(description = "몸무게 주차 조회")
    @Parameter(name = "AccessToken", in = ParameterIn.HEADER, required = true)
    @GetMapping("/week/{dogId}")
    public ResponseEntity<GetWeekWeightResponse> getWeekWeight(
            @PathVariable Long dogId,
            @DateTimeFormat(pattern = DATE_PATTERN) LocalDate date
    ) {
        return ResponseEntity.ok(weightService.getWeekWeight(dogId, date));
    }

    @Operation(description = "몸무게 월별 조회")
    @Parameter(name = "AccessToken", in = ParameterIn.HEADER, required = true)
    @GetMapping("/month/{dogId}")
    public ResponseEntity<GetMonthWeightResponse> getMonthWeight(
            @PathVariable Long dogId,
            @DateTimeFormat(pattern = DATE_PATTERN) LocalDate date
    ) {
        return ResponseEntity.ok(weightService.getMonthWeight(dogId, date));
    }

    @Operation(description = "몸무게 수정")
    @Parameter(name = "AccessToken", in = ParameterIn.HEADER, required = true)
    @PatchMapping("/{dogId}")
    public ResponseEntity<Void> updateWeight(@PathVariable Long dogId, @RequestParam double weight) {
        weightService.updateWeight(dogId, weight);
        return ResponseEntity.ok().build();
    }

    @Operation(description = "몸무게 데이터 추가")
    @Parameter(name = "AccessToken", in = ParameterIn.HEADER, required = true)
    @PostMapping
    public ResponseEntity<Void> saveWeight(@RequestBody @Valid SaveWeightRequest request) {
        weightService.saveWeight(request.getDogId(), request.getDate(), request.getKg());
        return ResponseEntity.ok().build();
    }

    @Operation(description = "당일 몸무게 조회")
    @Parameter(name = "AccessToken", in = ParameterIn.HEADER, required = true)
    @GetMapping("/day/{dogId}")
    public ResponseEntity<GetDayWeightResponse> getWeight(
            @PathVariable Long dogId,
            @DateTimeFormat(pattern = DATE_PATTERN) LocalDate date
    ) {
        return ResponseEntity.ok(weightService.getDayWeight(dogId, date));
    }

    @Operation(description = "반려견 메인 홈 몸무게 조회")
    @Parameter(name = "AccessToken", in = ParameterIn.HEADER, required = true)
    @GetMapping("/home/{dogId}")
    public ResponseEntity<GetWeightForHomeResponse> getWeightForHome(
            @PathVariable Long dogId,
            @RequestParam @DateTimeFormat(pattern = DATE_PATTERN) LocalDate date
    ) {
        return ResponseEntity.ok(weightService.getWeightForHome(dogId, date));
    }
}
