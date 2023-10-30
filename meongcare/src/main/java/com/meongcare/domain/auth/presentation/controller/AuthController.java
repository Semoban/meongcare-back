package com.meongcare.auth.presentation.controller;

import com.meongcare.auth.presentation.dto.request.LoginRequestDto;
import com.meongcare.auth.presentation.dto.response.LoginResponseDto;
import com.meongcare.auth.service.AuthService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
@NoArgsConstructor
public class AuthController {

    private AuthService authService;

    @GetMapping("/login")
    public ResponseEntity<LoginResponseDto> login(LoginRequestDto loginRequestDto) {
        LoginResponseDto loginResponseDto =  authService.login(loginRequestDto);

        return ResponseEntity.ok().body(loginResponseDto);
    }

}
