package com.community.demo.auth.temp;

import com.community.demo.exception.UnauthorizedException;
import com.community.demo.users.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuthenticationInterceptor implements HandlerInterceptor {

    private final UserService authService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("interceptor");
        log.info("requested URI: " + request.getRequestURI());
        HttpSession session = Optional.ofNullable(request.getSession(false)).orElseThrow(() -> {
            log.info("unauthorized in login interceptor");
            return new UnauthorizedException("unauthorized");
        });

        log.info("user: " + (session.getAttribute("loginUser")));

        if (session.getAttribute("loginUser") == null) {
            log.info("unauthorized");
            throw new UnauthorizedException("need-login");
        }

        Long userId = (Long) session.getAttribute("userId");

        return HandlerInterceptor.super.preHandle(request, response, handler);
    }
}
