package com.meongcare.domain.auth.application;

import com.meongcare.domain.member.domain.entity.Member;
import com.meongcare.domain.auth.domain.entity.RefreshToken;
import com.meongcare.domain.member.domain.repository.MemberRepository;
import com.meongcare.common.jwt.JwtService;
import com.meongcare.domain.auth.domain.repository.RefreshTokenRedisRepository;
import com.meongcare.domain.auth.presentation.dto.request.LoginRequestDto;
import com.meongcare.domain.auth.presentation.dto.response.LoginResponseDto;
import com.meongcare.domain.auth.presentation.dto.response.ReissueResponseDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class AuthService {

    private final JwtService jwtService;
    private final MemberRepository memberRepository;
    private final RefreshTokenRedisRepository refreshTokenRedisRepository;

    @Transactional
    public LoginResponseDto login(LoginRequestDto loginRequestDto) {
        String providerId = loginRequestDto.getProviderId();
        Optional<Member> findMemberOptional = memberRepository.findByProviderId(providerId);
        Long memberId = null;

        if (findMemberOptional.isEmpty()) {
            Member member = loginRequestDto.toMemberEntity();
            memberRepository.save(member);
            memberId = member.getId();
        }
        if (findMemberOptional.isPresent()) {
            memberId = findMemberOptional.get().getId();
        }

        String accessToken = jwtService.createAccessToken(memberId);
        String refreshToken = jwtService.createRefreshToken(memberId);

        refreshTokenRedisRepository.save(RefreshToken.of(refreshToken, memberId));

        LoginResponseDto loginResponseDto = LoginResponseDto.of(accessToken, refreshToken);

        return loginResponseDto;
    }

    public ReissueResponseDto reissue(String refreshToken) {
        Long userId = jwtService.parseJwtToken(refreshToken);

        refreshTokenRedisRepository
                .findById(refreshToken)
                .orElseThrow(IllegalArgumentException::new);

        String accessToken = jwtService.createAccessToken(userId);
        ReissueResponseDto reissueResponseDto = new ReissueResponseDto(accessToken);

        return reissueResponseDto;
    }

    public void logout(String refreshToken) {
        refreshTokenRedisRepository.deleteById(refreshToken);
    }

}
