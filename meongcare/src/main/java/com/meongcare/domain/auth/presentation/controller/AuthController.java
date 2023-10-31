package com.meongcare.domain.auth.presentation.controller;

import com.meongcare.common.jwt.JwtValidation;
import com.meongcare.domain.auth.presentation.dto.request.LoginRequestDto;
import com.meongcare.domain.auth.presentation.dto.response.LoginResponseDto;
import com.meongcare.domain.auth.presentation.dto.response.ReissueResponseDto;
import com.meongcare.domain.auth.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@Valid @RequestBody LoginRequestDto loginRequestDto) {
        LoginResponseDto loginResponseDto = authService.login(loginRequestDto);

        return ResponseEntity.ok().body(loginResponseDto);
    }

    @GetMapping("/reissue")
    public ResponseEntity<ReissueResponseDto> reissue(@RequestHeader("RefreshToken") String refreshToken) {
         ReissueResponseDto reissueResponseDto = authService.reissue(refreshToken);

        return ResponseEntity.ok().body(reissueResponseDto);
    }

    @DeleteMapping("/logout")
    public ResponseEntity login(@JwtValidation Long userId) {
        authService.logout(userId);
        return ResponseEntity.ok().build();
    }



}
