package com.meongcare.domain.auth.presentation.controller;

import com.meongcare.common.jwt.JwtValidation;
import com.meongcare.domain.auth.presentation.dto.request.LoginRequestDto;
import com.meongcare.domain.auth.presentation.dto.response.LoginResponseDto;
import com.meongcare.domain.auth.presentation.dto.response.ReissueResponseDto;
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
