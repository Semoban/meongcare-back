package com.meongcare.domain.dog.presentation.controller;

import com.meongcare.common.jwt.JwtValidation;
import com.meongcare.domain.dog.presentation.dto.request.SaveDogRequestDto;
import com.meongcare.domain.dog.service.DogService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dog")
@AllArgsConstructor
public class DogController {

    private DogService dogService;

    @PostMapping()
    public ResponseEntity saveDog(@JwtValidation Long userId, @RequestBody SaveDogRequestDto saveDogRequestDto) {
        dogService.saveDog(userId, saveDogRequestDto);
        return ResponseEntity.ok().build();
    }



}
