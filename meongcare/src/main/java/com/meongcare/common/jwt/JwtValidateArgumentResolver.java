package com.meongcare.common.jwt;

import com.meongcare.common.error.ErrorCode;
import com.meongcare.common.error.exception.clientError.InvalidTokenException;
import lombok.AllArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;

@Component
@AllArgsConstructor
public class JwtValidateArgumentResolver implements HandlerMethodArgumentResolver {
    private static final String ACCESS_TOKEN_HEADER = "AccessToken";

    private final JwtService jwtService;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        boolean hasJwtValidationAnnotation = parameter.hasParameterAnnotation(JwtValidation.class);
        boolean hasLongType = Long.class.isAssignableFrom(parameter.getParameterType());
        return hasJwtValidationAnnotation && hasLongType;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
        String accessToken = request.getHeader(ACCESS_TOKEN_HEADER);

        if (accessToken == null){
            throw new InvalidTokenException(ErrorCode.INVALID_ACCESS_TOKEN);
        }

        Long userIdx = jwtService.parseJwtToken(accessToken);
        return userIdx;
    }
}
