package com.meongcare.domain.member.domain.entity;

import lombok.Getter;

import java.util.stream.Stream;

@Getter
public enum Provider {
    GOOGLE("google"),
    NAVER("naver"),
    KAKAO("kakao");

    private final String provider;

    Provider(String provider) {
        this.provider = provider;
    }

    public static Provider of(String provider) {
        return Stream.of(Provider.values())
                .filter(p -> p.getProvider().equals(provider))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }

}
