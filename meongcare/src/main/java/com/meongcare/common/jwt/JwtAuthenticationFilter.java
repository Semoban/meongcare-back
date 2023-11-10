package com.meongcare.common.jwt;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final String ACCESS_TOKEN_HEADER = "AccessToken";

    private final JwtService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String accessToken = request.getHeader(ACCESS_TOKEN_HEADER);
            jwtService.parseJwtToken(accessToken);
        } catch (Exception e) {

            throw new IllegalArgumentException("유효하지 않은 토큰입니다.");
        }
        filterChain.doFilter(request, response);
    }
}
