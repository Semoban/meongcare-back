package com.meongcare.domain.dog.presentation.controller;

import com.meongcare.common.jwt.JwtValidation;
import com.meongcare.domain.dog.presentation.dto.request.SaveDogRequestDto;
import com.meongcare.domain.dog.presentation.dto.response.GetDogsResponseDto;
import com.meongcare.domain.dog.service.DogService;
import com.meongcare.domain.medicalrecord.presentation.dto.request.SaveMedicalRecordRequestDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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
}
