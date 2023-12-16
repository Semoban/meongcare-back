package com.meongcare.common.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.meongcare.common.error.ErrorCode;
import com.meongcare.common.error.ErrorResponse;
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

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String accessToken = request.getHeader(ACCESS_TOKEN_HEADER);
            jwtService.parseJwtToken(accessToken);
            filterChain.doFilter(request, response);
        } catch (Exception e) {
            setErrorResponse(response, ErrorCode.INVALID_ACCESS_TOKEN);
        }
    }

    private void setErrorResponse(HttpServletResponse response, ErrorCode errorCode) {
        ObjectMapper objectMapper = new ObjectMapper();
        response.setStatus(errorCode.getHttpStatus().value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(ENCODING_TYPE);
        ErrorResponse errorResponse = new ErrorResponse(errorCode.getHttpStatus().value(), errorCode.getMessage());
        try{
            response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
        }catch (IOException e){
            e.printStackTrace();
        }

    }
}
