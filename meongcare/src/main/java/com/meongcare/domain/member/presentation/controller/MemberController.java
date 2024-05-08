package com.meongcare.domain.member.presentation.controller;

import com.meongcare.common.jwt.JwtValidation;
import com.meongcare.domain.member.presentation.dto.request.EditProfileImageRequest;
import com.meongcare.domain.member.presentation.dto.response.GetProfileResponse;
import com.meongcare.domain.member.application.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Tag(name = "회원 API")
@RestController
@RequestMapping("/member")
@AllArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @Operation(description = "나의 정보 조회")
    @Parameter(name = "AccessToken", in = ParameterIn.HEADER, required = true)
    @GetMapping("/profile")
    public ResponseEntity<GetProfileResponse> getProfile(@JwtValidation Long memberId){
        GetProfileResponse getProfileResponse = memberService.getProfile(memberId);
        return ResponseEntity.ok().body(getProfileResponse);
    }

    @Operation(description = "프로필 사진 수정")
    @Parameter(name = "AccessToken", in = ParameterIn.HEADER, required = true)
    @PatchMapping("/profile")
    public ResponseEntity<Void> updateProfileImage(@JwtValidation Long memberId, @RequestBody @Valid EditProfileImageRequest request) {
        memberService.updateProfileImage(memberId, request);
        return ResponseEntity.ok().build();
    }

    @Operation(description = "알림 설정")
    @Parameter(name = "AccessToken", in = ParameterIn.HEADER, required = true)
    @PatchMapping("/alarm")
    public ResponseEntity<Void> updateAlarm(@JwtValidation Long memberId,
                                     @RequestParam("pushAgreement") boolean pushAgreement) {
        memberService.updateAlarm(memberId, pushAgreement);
        return ResponseEntity.ok().build();
    }

    @Operation(description = "회원 탈퇴")
    @Parameter(name = "AccessToken", in = ParameterIn.HEADER, required = true)
    @DeleteMapping
    public ResponseEntity<Void> deleteMember(@JwtValidation Long memberId){
        memberService.deleteMember(memberId);
        return ResponseEntity.ok().build();
    }

    @Operation(description = "계정 존재 확인")
    @Parameter(name = "email", in = ParameterIn.PATH, required = true)
    @GetMapping("/exist/{email}")
    public ResponseEntity<Void> checkExistMember(@PathVariable String email){
        memberService.checkExistMember(email);
        return ResponseEntity.ok().build();
    }
}
