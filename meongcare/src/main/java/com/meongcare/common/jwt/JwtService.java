package com.meongcare.common.jwt;

import com.meongcare.common.error.ErrorCode;
import com.meongcare.common.error.exception.clientError.InvalidTokenException;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.Date;

@Service
@RequiredArgsConstructor
@Slf4j
public class JwtService {

    @Value("${jwt.secretKey}")
    private String secretKey;

    @Value("${jwt.access.expiration}")
    private Long accessTokenExpirationPeriod;

    @Value("${jwt.refresh.expiration}")
    private Long refreshTokenExpirationPeriod;

    @Value("${jwt.access.header}")
    private String ACCESS_TOKEN_SUBJECT;

    @Value("${jwt.refresh.header}")
    private String REFRESH_TOKEN_SUBJECT;

    private static final String ID_CLAIM = "id";
    private static final String BEARER = "Bearer ";

    public String createAccessToken(Long userId) {
        String accessToken = createToken(userId, ACCESS_TOKEN_SUBJECT, accessTokenExpirationPeriod);
        return accessToken;
    }

    public String createRefreshToken(Long userId) {
        String refreshToken = createToken(userId, REFRESH_TOKEN_SUBJECT, refreshTokenExpirationPeriod);
        return refreshToken;
    }

    private String createToken(Long userId, String tokenSubject, Long expirationPeriod) {
        Date expirationTime = new Date();
        Claims claims = Jwts.claims();
        expirationTime.setTime(expirationTime.getTime() + expirationPeriod);
        claims.put(ID_CLAIM, userId);

        String accessToken = Jwts.builder()
                .setSubject(tokenSubject)
                .setClaims(claims)
                .setExpiration(expirationTime)
                .signWith(SignatureAlgorithm.HS256, Base64.getEncoder().encodeToString(secretKey.getBytes()))
                .compact();
        return BEARER + accessToken;
    }

    public Long parseJwtToken(String token) {
        try {
            token = BearerRemove(token);
            Claims claims = Jwts.parser()
                    .setSigningKey(Base64.getEncoder().encodeToString(secretKey.getBytes()))
                    .parseClaimsJws(token)
                    .getBody();

            return Long.valueOf((Integer) claims.get(ID_CLAIM));
        } catch (Exception e) {
            throw new InvalidTokenException(ErrorCode.INVALID_TOKEN);
        }
    }

    private String BearerRemove(String token) {
        return token.substring(BEARER.length());
    }

}
