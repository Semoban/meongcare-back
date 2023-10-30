package com.meongcare.domain.auth.service;

import com.meongcare.domain.auth.domain.entity.Member;
import com.meongcare.domain.auth.domain.repository.MemberRepository;
import com.meongcare.common.jwt.JwtService;
import com.meongcare.domain.auth.presentation.dto.request.LoginRequestDto;
import com.meongcare.domain.auth.presentation.dto.response.LoginResponseDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class AuthService {

    private JwtService jwtService;
    private MemberRepository memberRepository;

    public LoginResponseDto login(LoginRequestDto loginRequestDto) {

        String providerId = loginRequestDto.getProviderId();

        Optional<Member> findMemberOptional = memberRepository.findByProviderId(providerId);

        Long memberId = null;
        if (findMemberOptional.isEmpty()) {
            Member member = loginRequestDto.toMemberEntity();
            memberRepository.save(member);
            memberId = member.getId();
        }

        if (findMemberOptional.isPresent()){
            memberId = findMemberOptional.get().getId();
        }

        LoginResponseDto loginResponseDto = createLoginResponseDto(memberId);

        return loginResponseDto;
    }


    private LoginResponseDto createLoginResponseDto(Long memberId) {
        String accessToken = jwtService.createAccessToken(memberId);
        String refreshToken = jwtService.createRefreshToken(memberId);

        LoginResponseDto signUpResponseDto = LoginResponseDto
                .builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
        return signUpResponseDto;
    }
}
