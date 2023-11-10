package com.meongcare.domain.dog.presentation.controller;

import com.meongcare.common.jwt.JwtValidation;
import com.meongcare.domain.dog.presentation.dto.request.PutDogRequestDto;
import com.meongcare.domain.dog.presentation.dto.request.SaveDogRequestDto;
import com.meongcare.domain.dog.presentation.dto.response.GetDogResponseDto;
import com.meongcare.domain.dog.presentation.dto.response.GetDogsResponseDto;
import com.meongcare.domain.dog.application.DogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

@Tag(name = "강아지 API")
@RestController
@RequestMapping("/dog")
@AllArgsConstructor
public class DogController {

    private final DogService dogService;

    @Operation(description = "강아지 저장")
    @PostMapping
    public ResponseEntity saveDog(
            @RequestPart(value = "file") MultipartFile multipartFile,
            @RequestPart(value = "dto") @Valid SaveDogRequestDto saveDogRequestDto,
            @JwtValidation Long userId) {
        dogService.saveDog(multipartFile, saveDogRequestDto, userId);
        return ResponseEntity.ok().build();
    }

    @Operation(description = "반려동물 목록")
    @GetMapping
    public ResponseEntity<GetDogsResponseDto> getDogs(@JwtValidation Long userId) {
        GetDogsResponseDto getDogsResponseDto = dogService.getDogs(userId);
        return ResponseEntity.ok().body(getDogsResponseDto);
    }

    @Operation(description = "반려동물 상세 조회")
    @Parameter(name = "AccessToken", in = ParameterIn.HEADER, required = true)
    @GetMapping("/{dogId}")
    public ResponseEntity<GetDogResponseDto> getDog(
            @PathVariable Long dogId) {
        GetDogResponseDto getDogResponseDto = dogService.getDog(dogId);
        return ResponseEntity.ok().body(getDogResponseDto);
    }

    @Operation(description = "강아지 저장")
    @Parameter(name = "AccessToken", in = ParameterIn.HEADER, required = true)
    @PutMapping("/{dogId}")
    public ResponseEntity updateDog(
            @RequestPart(value = "file") MultipartFile multipartFile,
            @RequestPart(value = "dto") @Valid PutDogRequestDto putDogRequestDto,
            @PathVariable Long dogId) {
        dogService.updateDog(multipartFile, putDogRequestDto, dogId);
        return ResponseEntity.ok().build();
    }

    @Operation(description = "강아지 삭제")
    @Parameter(name = "AccessToken", in = ParameterIn.HEADER, required = true)
    @DeleteMapping("/{dogId}")
    public ResponseEntity updateDog(@PathVariable Long dogId) {
        dogService.deleteDog(dogId);
        return ResponseEntity.ok().build();
    }
}
