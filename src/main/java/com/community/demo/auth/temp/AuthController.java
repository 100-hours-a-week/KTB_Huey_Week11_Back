package com.community.demo.auth.temp;

import com.community.demo.ApiResponse;
import com.community.demo.auth.temp.dto.LoginRequestDto;
import com.community.demo.users.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<Void>> login(LoginRequestDto request) {
        authService.login(request);

        return ResponseEntity
                .ok()
                .body(ApiResponse.of("login_success", null));
    }
}
