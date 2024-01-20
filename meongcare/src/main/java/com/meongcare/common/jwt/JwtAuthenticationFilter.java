package com.meongcare.common.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.meongcare.common.error.ErrorCode;
import com.meongcare.common.error.ErrorResponse;
import com.meongcare.domain.member.domain.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private static final String ACCESS_TOKEN_HEADER = "AccessToken";
    private static final String ENCODING_TYPE = "UTF-8";

    private final JwtService jwtService;
    private final MemberRepository memberRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String accessToken = request.getHeader(ACCESS_TOKEN_HEADER);
            Long userId = jwtService.parseJwtToken(accessToken);
            memberRepository.getById(userId);
            filterChain.doFilter(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            setErrorResponse(response, ErrorCode.INVALID_ACCESS_TOKEN);
        }
    }

    private void setErrorResponse(HttpServletResponse response, ErrorCode errorCode) {
        ObjectMapper objectMapper = new ObjectMapper();
        response.setStatus(errorCode.getHttpStatus().value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(ENCODING_TYPE);
        ErrorResponse errorResponse = new ErrorResponse(errorCode);
        try{
            response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
        }catch (IOException e){
            e.printStackTrace();
        }

    }
}
