package com.community.demo.auth.temp;

import com.community.demo.ApiResponse;
import com.community.demo.auth.temp.dto.LoginResponseDto;
import com.community.demo.users.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class LegacyAuthController {

    private final UserService userService;

    @PostMapping("/login")
    public void login() {

    }

    @GetMapping("/logout")
    public void logout() {

    }

}
