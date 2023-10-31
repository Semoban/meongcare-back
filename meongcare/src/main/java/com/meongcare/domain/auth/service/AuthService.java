package com.meongcare.domain.auth.service;

import com.meongcare.domain.auth.domain.entity.Member;
import com.meongcare.domain.auth.domain.entity.RefreshToken;
import com.meongcare.domain.auth.domain.repository.MemberRepository;
import com.meongcare.common.jwt.JwtService;
import com.meongcare.domain.auth.domain.repository.RefreshTokenRedisRepository;
import com.meongcare.domain.auth.presentation.dto.request.LoginRequestDto;
import com.meongcare.domain.auth.presentation.dto.response.LoginResponseDto;
import com.meongcare.domain.auth.presentation.dto.response.ReissueResponseDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AuthService {

    private final JwtService jwtService;
    private final MemberRepository memberRepository;
    private final RefreshTokenRedisRepository refreshTokenRedisRepository;

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

        String accessToken = jwtService.createAccessToken(memberId);
        String refreshToken = jwtService.createRefreshToken(memberId);

        refreshTokenRedisRepository.save(RefreshToken
                .builder()
                .id(memberId)
                .refreshToken(refreshToken)
                .build());


        LoginResponseDto loginResponseDto = LoginResponseDto
                .builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();

        return loginResponseDto;
    }
    public ReissueResponseDto reissue(String refreshToken) {
        Long userId = jwtService.parseJwtToken(refreshToken);
        Optional<RefreshToken> findRefreshToken = refreshTokenRedisRepository.findById(userId);

        if (findRefreshToken.isEmpty()) {
            throw new EntityNotFoundException("토큰이 만료 되었습니다.");
        }

        String accessToken =jwtService.createAccessToken(userId);
        ReissueResponseDto reissueResponseDto = new ReissueResponseDto(accessToken);

        return reissueResponseDto;
    }

    public void logout(Long userId) {
        refreshTokenRedisRepository.deleteById(userId);
    }

}
