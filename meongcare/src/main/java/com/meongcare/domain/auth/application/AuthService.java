package com.meongcare.domain.auth.application;

import com.meongcare.common.error.ErrorCode;
import com.meongcare.common.error.exception.clientError.InvalidTokenException;
import com.meongcare.common.error.exception.clientError.UnauthorizedException;
import com.meongcare.domain.member.domain.entity.Member;
import com.meongcare.domain.auth.domain.entity.RefreshToken;
import com.meongcare.domain.member.domain.repository.MemberRepository;
import com.meongcare.common.jwt.JwtService;
import com.meongcare.domain.auth.domain.repository.RefreshTokenRedisRepository;
import com.meongcare.domain.auth.presentation.dto.request.LoginRequest;
import com.meongcare.domain.auth.presentation.dto.response.LoginResponse;
import com.meongcare.domain.auth.presentation.dto.response.ReissueResponse;
import com.meongcare.domain.member.domain.repository.RevokeMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthService {

    private final JwtService jwtService;
    private final MemberRepository memberRepository;
    private final RefreshTokenRedisRepository refreshTokenRedisRepository;
    private final RevokeMemberRepository revokeMemberRepository;
    private static final String PROVIDER_ID_SEPARATOR = "@";

    @Transactional
    public LoginResponse login(LoginRequest loginRequest) {
        String providerIdWithProvider = loginRequest.getProviderId() + PROVIDER_ID_SEPARATOR + loginRequest.getProvider();
        checkIsRevokeUser(providerIdWithProvider);

        Optional<Member> findMemberOptional = memberRepository.findByProviderId(providerIdWithProvider);
        Long memberId;
        Boolean isFirstLogin = false;
        if (findMemberOptional.isEmpty()) {
            memberId = joinMember(loginRequest, providerIdWithProvider);
            isFirstLogin = true;
        } else {
            Member member = findMemberOptional.get();
            memberId = member.getId();
            checkFcmToken(loginRequest, member);
        }

        String accessToken = jwtService.createAccessToken(memberId);
        String refreshToken = jwtService.createRefreshToken(memberId);

        refreshTokenRedisRepository.save(RefreshToken.of(refreshToken, memberId));

        LoginResponse loginResponse = LoginResponse.of(accessToken, refreshToken, isFirstLogin);

        return loginResponse;
    }

    private void checkFcmToken(LoginRequest loginRequest, Member member) {
        if (member.isNewFcmToken(loginRequest.getFcmToken())) {
            member.updateFcmToken(loginRequest.getFcmToken());
        }
    }

    private Long joinMember(LoginRequest loginRequest, String providerIdWithProvider) {
        Member member = loginRequest.toMemberEntity(providerIdWithProvider);
        memberRepository.save(member);
        return member.getId();
    }

    private void checkIsRevokeUser(String providerId) {
        if (revokeMemberRepository.existsByProviderId(providerId)) {
            throw new UnauthorizedException(ErrorCode.REVOKE_MEMBER_NOT_ALLOWED_LOGIN);
        }
    }

    public ReissueResponse reissue(String refreshToken) {
        Long userId = jwtService.parseJwtToken(refreshToken);
        refreshTokenRedisRepository
                .findById(refreshToken)
                .orElseThrow(() -> new InvalidTokenException(ErrorCode.INVALID_REFRESH_TOKEN));

        String accessToken = jwtService.createAccessToken(userId);
        ReissueResponse reissueResponse = new ReissueResponse(accessToken);

        return reissueResponse;
    }

    @Transactional
    public void logout(String refreshToken) {
        Long userId = jwtService.parseJwtToken(refreshToken);
        Member member = memberRepository.getUser(userId);
        member.deleteFcmToken();
        refreshTokenRedisRepository.deleteById(refreshToken);
    }
}
