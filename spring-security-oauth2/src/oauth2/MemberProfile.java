package com.meongcare.auth.oauth2;

import com.meongcare.auth.domain.entity.Member;
import lombok.Getter;

@Getter
public class MemberProfile {

    private final String provider;
    private final String email;
    private final String name;
    private final String profileImageUrl;

    public MemberProfile(String provider, String name, String email, String profileImageUrl) {
        this.provider = provider;
        this.name = name;
        this.email = email;
        this.profileImageUrl = profileImageUrl;
    }

}