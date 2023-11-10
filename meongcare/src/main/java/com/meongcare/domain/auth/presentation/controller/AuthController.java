package com.meongcare.domain.auth.presentation.controller;

import com.meongcare.common.jwt.JwtValidation;
import com.meongcare.domain.auth.presentation.dto.request.LoginRequestDto;
import com.meongcare.domain.auth.presentation.dto.response.GetProfileResponseDto;
import com.meongcare.domain.auth.presentation.dto.response.LoginResponseDto;
import com.meongcare.domain.auth.presentation.dto.response.ReissueResponseDto;
import com.meongcare.domain.auth.application.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Tag(name = "회원 API")
@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {

    private final AuthService authService;

    @Operation(description = "회원가입/로그인 (토큰 발급)")
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@Valid @RequestBody LoginRequestDto loginRequestDto) {
        LoginResponseDto loginResponseDto = authService.login(loginRequestDto);

        return ResponseEntity.ok().body(loginResponseDto);
    }

    @Operation(description = "엑세스 토큰 재발급")
    @GetMapping("/reissue")
    public ResponseEntity<ReissueResponseDto> reissue(@RequestHeader("RefreshToken") String refreshToken) {
         ReissueResponseDto reissueResponseDto = authService.reissue(refreshToken);

        return ResponseEntity.ok().body(reissueResponseDto);
    }

    @Operation(description = "로그아웃 (엑세스 토큰 삭제)")
    @DeleteMapping("/logout")
    public ResponseEntity login(@RequestHeader("RefreshToken") String refreshToken) {
        authService.logout(refreshToken);
        return ResponseEntity.ok().build();
    }

    @Operation(description = " 나의 정보 조회")
    @GetMapping("/profile")
    public ResponseEntity<GetProfileResponseDto> getProfile(@JwtValidation Long userId){
        GetProfileResponseDto getProfileResponseDto = authService.getProfile(userId);
        return ResponseEntity.ok().body(getProfileResponseDto);
    }




}
