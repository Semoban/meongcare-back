package com.meongcare.auth.oauth2;

import com.meongcare.auth.domain.entity.Member;
import com.meongcare.auth.domain.repository.MemberRepository;
import com.meongcare.auth.oauth2.token.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class OAuth2LoginSuccessHandler implements AuthenticationSuccessHandler {

    private final JwtService jwtService;
    private final MemberRepository memberRepository;



    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        log.info("OAuth2 Login 성공!");
        try {
            CustomOAuth2User userDetails = ((CustomOAuth2User) authentication.getPrincipal());
            MemberProfile memberProfile = userDetails.getMemberProfile();

            Optional<Member> findMember = memberRepository.findByEmail(memberProfile.getEmail());
            Member member;
            if(findMember.isEmpty()) {
                member = Member
                        .builder()
                        .name(memberProfile.getName())
                        .email(memberProfile.getEmail())
                        .provider(memberProfile.getProvider())
                        .profileImage(memberProfile.getProfileImageUrl())
                        .pushAgreement(false)
                        .refreshToken(" ")
                        .fcmToken(" ")
                        .build();
                memberRepository.save(member);
            } else {
                member = findMember.get();
            }
            String accessToken = jwtService.createAccessToken(member.getId());
            String refreshToken = jwtService.createRefreshToken(member.getId());
            member.updateRefreshToken(refreshToken);
            jwtService.sendAccessAndRefreshToken(response, accessToken, refreshToken);
        } catch (Exception e) {
            throw e;
        }

    }
}