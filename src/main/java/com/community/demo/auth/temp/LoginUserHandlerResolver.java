package com.community.demo.auth.temp;

import com.community.demo.exception.NotFoundException;
import com.community.demo.exception.UnauthorizedException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.Nullable;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.Optional;

@Slf4j
@Component
public class LoginUserHandlerResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        log.info("LoginUserHandlerResolver: method - " + parameter.getMethod().getName() + "." + parameter.getParameterName());
        log.info("LoginUserHandlerResolver.supportsParameter: " + parameter.hasParameterAnnotation(Login.class));
        return parameter.hasParameterAnnotation(Login.class);
    }

    @Override
    public @Nullable Object resolveArgument(MethodParameter parameter, @Nullable ModelAndViewContainer mavContainer, NativeWebRequest webRequest, @Nullable WebDataBinderFactory binderFactory) throws Exception {
        log.info("argument resolver");
        HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
        HttpSession session = Optional.ofNullable(request.getSession(false))
                .orElseThrow(() -> new UnauthorizedException("unauthorized"));
        log.info("is resolver session null: " + (session == null));
        return Optional.ofNullable(session.getAttribute("loginUser"))
                .orElseThrow(() -> new NotFoundException("user_info_not_found"));
    }
}
