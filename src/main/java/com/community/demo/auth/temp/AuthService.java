package com.community.demo.auth.temp;

import com.community.demo.auth.temp.dto.LoginRequestDto;
import com.community.demo.comments.CommentController;
import com.community.demo.exception.BadRequestException;
import com.community.demo.exception.NotFoundException;
import com.community.demo.users.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.method.HandlerMethod;

import java.lang.reflect.Method;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthRepository authRepository;
    private final UserRepository userRepository;

    public void login(LoginRequestDto request, HttpServletRequest httpRequest) {
        Auth auth = authRepository.findByEmail(request.getEmail()).orElseThrow(() -> new NotFoundException("user_not_found"));

        if (!request.getPassword().equals(auth.getPassword())) {
            throw new BadRequestException("bad_request");
        }

        //세션 생성
        HttpSession session = httpRequest.getSession();

        //세션에 userId 주입
        session.setAttribute("userId", auth.getUser().getId());
        session.setMaxInactiveInterval(1800);
    }

    public void logout() {

    }

    public Boolean authenticate(Long userId) {
        userRepository.findById(userId).orElseThrow(() -> new NotFoundException("user_not_found"));
        return true;
    }
}
