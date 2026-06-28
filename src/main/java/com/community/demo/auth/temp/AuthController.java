package com.community.demo.auth.temp;

import com.community.demo.auth.temp.dto.controller.LoginAuthControllerRequestDto;
import com.community.demo.auth.temp.dto.controller.LoginAuthControllerResponseDto;
import com.community.demo.auth.temp.dto.controller.SignupAuthControllerRequestDto;
import com.community.demo.auth.temp.dto.controller.SignupAuthControllerResponseDto;
import com.community.demo.users.UserService;
import com.community.demo.auth.temp.dto.controller.ModifyPasswordAuthControllerRequestDto;
import com.community.demo.auth.temp.dto.controller.ModifyPasswordAuthControllerResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final UserService userService;

    @PostMapping("/signup")
    public SignupAuthControllerResponseDto signup(SignupAuthControllerRequestDto dto) {
        return new SignupAuthControllerResponseDto();
    }

    @PostMapping("/login")
    public LoginAuthControllerResponseDto login(LoginAuthControllerRequestDto dto) {
        return new LoginAuthControllerResponseDto();
    }

    @PatchMapping("/{user_id}/password")
    public ModifyPasswordAuthControllerResponseDto modifyPassword(ModifyPasswordAuthControllerRequestDto dto) {
        return new ModifyPasswordAuthControllerResponseDto();
    }

}
