package com.meongcare.domain.member.presentation.controller;

import com.meongcare.common.jwt.JwtValidation;
import com.meongcare.domain.dog.presentation.dto.request.PutDogRequest;
import com.meongcare.domain.member.presentation.dto.response.GetProfileResponse;
import com.meongcare.domain.member.application.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
    public ResponseEntity<GetProfileResponse> getProfile(@JwtValidation Long userId){
        GetProfileResponse getProfileResponse = memberService.getProfile(userId);
        return ResponseEntity.ok().body(getProfileResponse);
    }

    @Operation(description = "프로필 사진 수정")
    @Parameter(name = "AccessToken", in = ParameterIn.HEADER, required = true)
    @PatchMapping("/profile")
    public ResponseEntity<Void> updateProfileImage(@JwtValidation Long userId,
                                           @RequestPart(value = "file") MultipartFile multipartFile){
        memberService.updateProfileImage(userId, multipartFile);
        return ResponseEntity.ok().build();
    }

    @Operation(description = "알림 설정")
    @Parameter(name = "AccessToken", in = ParameterIn.HEADER, required = true)
    @PatchMapping("/alarm")
    public ResponseEntity<Void> updateAlarm(@JwtValidation Long userId,
                                     @RequestParam("pushAgreement") boolean pushAgreement) {
        memberService.updateAlarm(userId, pushAgreement);
        return ResponseEntity.ok().build();
    }

    @Operation(description = "회원 탈퇴")
    @Parameter(name = "AccessToken", in = ParameterIn.HEADER, required = true)
    @DeleteMapping
    public ResponseEntity<Void> deleteMember(@JwtValidation Long userId){
        memberService.deleteMember(userId);
        return ResponseEntity.ok().build();
    }
}
