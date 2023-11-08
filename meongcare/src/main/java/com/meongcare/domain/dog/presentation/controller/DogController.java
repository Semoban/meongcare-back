package com.meongcare.domain.dog.presentation.controller;

import com.meongcare.common.jwt.JwtValidation;
import com.meongcare.domain.dog.presentation.dto.request.SaveDogRequestDto;
import com.meongcare.domain.dog.service.DogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Tag(name = "강아지 API")
@RestController
@RequestMapping("/dog")
@AllArgsConstructor
public class DogController {

    private final DogService dogService;

    @Operation(description = "강아지 저장")
    @PostMapping
    public ResponseEntity saveDog(@JwtValidation Long userId, @Valid @RequestBody SaveDogRequestDto saveDogRequestDto) {
        dogService.saveDog(userId, saveDogRequestDto);
        return ResponseEntity.ok().build();
    }



}
