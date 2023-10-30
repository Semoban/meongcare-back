package com.meongcare.common.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
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
    private String accessHeader;

    @Value("${jwt.refresh.header}")
    private String refreshHeader;

    private static final String ACCESS_TOKEN_SUBJECT = "AccessToken";
    private static final String REFRESH_TOKEN_SUBJECT = "RefreshToken";
    private static final String ID_CLAIM = "id";
    private static final String EXP_CLAIM = "exp";
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
        Date now = new Date();
        Claims claims = Jwts.claims();
        claims.put(ID_CLAIM, userId);
        claims.put(EXP_CLAIM, now.getTime() + expirationPeriod);

        String accessToken = Jwts.builder()
                .setSubject(tokenSubject)
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS256, Base64.getEncoder().encodeToString(secretKey.getBytes()))
                .compact();
        return accessToken;
    }

    public Long parseJwtToken(String token) {
        token = BearerRemove(token);
        Claims claims = Jwts.parser()
                .setSigningKey(Base64.getEncoder().encodeToString(secretKey.getBytes()))
                .parseClaimsJws(token)
                .getBody();
        if (new Date().getTime() > (long) claims.get(EXP_CLAIM)) {
            new IllegalArgumentException("유효하지 않은 토큰입니다.");
        }
        return Long.valueOf((Integer)claims.get(ID_CLAIM));
    }

    private String BearerRemove(String token) {
        return token.substring(BEARER.length());
    }

}
