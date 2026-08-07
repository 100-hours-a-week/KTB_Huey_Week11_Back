package com.community.demo.session;

import com.community.demo.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class SessionController {

    private final SessionService sessionService;

    @GetMapping("/users/me")
    public ResponseEntity<ApiResponse<UserInfoDto>> getUser(@CurrentSecurityContext SecurityContext context) {
        UserInfoDto response = sessionService.getUser(context.getAuthentication());

        return ResponseEntity
                .ok()
                .body(ApiResponse.of("authenticated", response));
    }
}
