package com.meongcare.domain.auth.presentation.controller;

import com.meongcare.domain.auth.presentation.dto.request.LoginRequestDto;
import com.meongcare.domain.auth.presentation.dto.response.LoginResponseDto;
import com.meongcare.domain.auth.service.AuthService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
@NoArgsConstructor
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto loginRequestDto) {
        LoginResponseDto loginResponseDto = authService.login(loginRequestDto);

        return ResponseEntity.ok().body(loginResponseDto);
    }



}
