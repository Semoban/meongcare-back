package com.meongcare.domain.auth.presentation.controller;

import com.meongcare.domain.auth.presentation.dto.request.LoginRequest;
import com.meongcare.domain.auth.presentation.dto.response.LoginResponse;
import com.meongcare.domain.auth.presentation.dto.response.ReissueResponse;
import com.meongcare.domain.auth.application.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Tag(name = "로그인 API")
@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {

    private final AuthService authService;

    @Operation(description = "회원가입/로그인 (토큰 발급)")
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        LoginResponse loginResponse = authService.login(loginRequest);
        return ResponseEntity.ok().body(loginResponse);
    }

    @Operation(description = "엑세스 토큰 재발급")
    @GetMapping("/reissue")
    public ResponseEntity<ReissueResponse> reissue(@RequestHeader("RefreshToken") String refreshToken) {
         ReissueResponse reissueResponse = authService.reissue(refreshToken);
        return ResponseEntity.ok().body(reissueResponse);
    }

    @Operation(description = "로그아웃 (엑세스 토큰 삭제)")
    @DeleteMapping("/logout")
    public ResponseEntity login(@RequestHeader("RefreshToken") String refreshToken) {
        authService.logout(refreshToken);
        return ResponseEntity.ok().build();
    }

}
