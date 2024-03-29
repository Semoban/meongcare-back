package com.meongcare.domain.dog.presentation.controller;

import com.meongcare.common.jwt.JwtValidation;
import com.meongcare.domain.dog.presentation.dto.request.PutDogRequest;
import com.meongcare.domain.dog.presentation.dto.request.SaveDogRequest;
import com.meongcare.domain.dog.presentation.dto.response.GetDogResponse;
import com.meongcare.domain.dog.presentation.dto.response.GetDogsResponse;
import com.meongcare.domain.dog.application.DogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

@Tag(name = "강아지 API")
@RestController
@RequestMapping("/dog")
@RequiredArgsConstructor
public class DogController {

    private final DogService dogService;

    @Operation(description = "강아지 저장")
    @Parameter(name = "AccessToken", in = ParameterIn.HEADER, required = true)
    @PostMapping
    @Valid
    public ResponseEntity<Void> saveDog(
            @RequestPart(value = "file") MultipartFile multipartFile,
            @Valid @RequestPart(value = "dto") SaveDogRequest saveDogRequest,
            @JwtValidation Long memberId) {
        dogService.saveDog(multipartFile, saveDogRequest, memberId);
        return ResponseEntity.ok().build();
    }

    @Operation(description = "반려동물 목록")
    @Parameter(name = "AccessToken", in = ParameterIn.HEADER, required = true)
    @GetMapping
    public ResponseEntity<GetDogsResponse> getDogs(@JwtValidation Long memberId) {
        GetDogsResponse getDogsResponse = dogService.getDogs(memberId);
        return ResponseEntity.ok().body(getDogsResponse);
    }

    @Operation(description = "반려동물 상세 조회")
    @Parameter(name = "AccessToken", in = ParameterIn.HEADER, required = true)
    @GetMapping("/{dogId}")
    public ResponseEntity<GetDogResponse> getDog(
            @PathVariable Long dogId) {
        GetDogResponse getDogResponse = dogService.getDog(dogId);
        return ResponseEntity.ok().body(getDogResponse);
    }

    @Operation(description = "강아지 수정")
    @Parameter(name = "AccessToken", in = ParameterIn.HEADER, required = true)
    @PutMapping("/{dogId}")
    public ResponseEntity<Void> updateDog(
            @RequestPart(value = "file") MultipartFile multipartFile,
            @RequestPart(value = "dto") @Valid PutDogRequest putDogRequest,
            @PathVariable Long dogId) {
        dogService.updateDog(multipartFile, putDogRequest, dogId);
        return ResponseEntity.ok().build();
    }

    @Operation(description = "강아지 삭제")
    @Parameter(name = "AccessToken", in = ParameterIn.HEADER, required = true)
    @DeleteMapping("/{dogId}")
    public ResponseEntity<Void> updateDog(@PathVariable Long dogId) {
        dogService.deleteDog(dogId);
        return ResponseEntity.ok().build();
    }
}
