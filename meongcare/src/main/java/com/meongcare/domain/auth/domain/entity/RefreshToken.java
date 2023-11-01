package com.meongcare.domain.auth.domain.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import javax.validation.constraints.NotNull;

@Builder
@Getter
@AllArgsConstructor
@RedisHash(value = "refreshToken", timeToLive = 1209600000)
public class RefreshToken {

    @Id
    @NotNull
    private String refreshToken;

    @NotNull
    private Long id;


    public static RefreshToken of(String refreshToken, Long id) {
        return RefreshToken
                .builder()
                .refreshToken(refreshToken)
                .id(id)
                .build();
    }

}
