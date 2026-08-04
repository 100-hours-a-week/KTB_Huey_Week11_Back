package com.community.demo.auth.security;

import com.community.demo.ApiResponse;
import com.community.demo.users.UserService;
import com.community.demo.users.dto.UserInfoDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @GetMapping("/csrf")
    public ResponseEntity<ApiResponse<CsrfDto>> requestCsrfToken(CsrfToken csrfToken, Authentication authentication) {
        if (authentication == null) {
            return ResponseEntity
                    .ok(ApiResponse.of("anonymous_csrf_token_publish_success", CsrfDto.ofAnonymous(csrfToken)));
        }
        CustomUserDetails user = (CustomUserDetails) authentication.getPrincipal();
        UserInfoDto userInfo = userService.getUser(user.getUsername());
        return ResponseEntity
                .ok(ApiResponse.of("user_csrf_token_publish_success", CsrfDto.of(csrfToken)));
    }
}
