package com.meongcare.domain.member.presentation.controller;

import com.meongcare.common.jwt.JwtValidation;
import com.meongcare.domain.member.presentation.dto.response.GetProfileResponse;
import com.meongcare.domain.member.application.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "회원 API")
@RestController
@RequestMapping("/member")
@AllArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @Operation(description = "나의 정보 조회")
    @GetMapping("/profile")
    public ResponseEntity<GetProfileResponse> getProfile(@JwtValidation Long userId){
        GetProfileResponse getProfileResponse = memberService.getProfile(userId);
        return ResponseEntity.ok().body(getProfileResponse);
    }

    @Operation(description = "알림 설정")
    @PatchMapping("/alarm")
    public ResponseEntity<Void> updateAlarm(@JwtValidation Long userId,
                                     @RequestParam("pushAgreement") boolean pushAgreement) {
        memberService.updateAlarm(userId, pushAgreement);
        return ResponseEntity.ok().build();
    }

    @Operation(description = "회원 탈퇴")
    @DeleteMapping
    public ResponseEntity<Void> deleteMember(@JwtValidation Long userId){
        memberService.deleteMember(userId);
        return ResponseEntity.ok().build();
    }
}
