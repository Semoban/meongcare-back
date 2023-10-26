package com.meongcare.auth.oauth2;

import com.meongcare.auth.domain.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        // OAuth 서비스(github, google, naver)에서 가져온 유저 정보를 담고있음
        OAuth2User oAuth2User = super.loadUser(userRequest);
        // OAuth 서비스 이름(ex. github, naver, google)
        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        // OAuth 로그인 시 키 값. 구글, 네이버, 카카오 등 각 다르기 때문에 변수로 받아서 넣음
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails()
                .getUserInfoEndpoint().getUserNameAttributeName();

        // OAuth2 서비스의 유저 정보들
        Map<String, Object> attributes = oAuth2User.getAttributes();

        // registrationId에 따라 유저 정보를 통해 공통된 UserProfile 객체로 만들어 줌
        MemberProfile memberProfile = OAuth2Attributes.extract(registrationId, attributes);


        return createCustomOAuth2User(attributes, userNameAttributeName, memberProfile);
    }

    private CustomOAuth2User createCustomOAuth2User(Map<String, Object> attributes,
                                                      String userNameAttributeName, MemberProfile memberProfile) {
        return new CustomOAuth2User(
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")),
                attributes,
                userNameAttributeName,
                memberProfile
        );
    }
}