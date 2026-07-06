package com.community.demo.auth.temp;

import com.community.demo.ApiResponse;
import com.community.demo.auth.temp.dto.LoginRequestDto;
import com.community.demo.auth.temp.dto.LoginResponseDto;
import com.community.demo.users.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class AuthController {

    private final UserService authService;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponseDto>> login(LoginRequestDto request, HttpServletRequest httpRequest) {
        LoginResponseDto loginUser = authService.login(request, httpRequest);
        return ResponseEntity
                .ok()
                .body(ApiResponse.of("login_success", loginUser));
    }

    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<Void>> logout(HttpServletRequest httpRequest) {
        return ResponseEntity
                .ok()
                .body(ApiResponse.of("logout_success", null));
    }
}
