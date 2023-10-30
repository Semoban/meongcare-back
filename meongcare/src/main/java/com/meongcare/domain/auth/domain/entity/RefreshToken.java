package com.meongcare.domain.auth.domain.entity;

import lombok.*;
import org.springframework.data.redis.core.RedisHash;

import javax.persistence.Id;

@Builder
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@RedisHash(value = "refresh", timeToLive = 1209600000)
public class RefreshToken {

    @Id
    private String id;

    private String refreshToken;
}
