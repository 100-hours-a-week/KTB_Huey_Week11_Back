package com.community.demo.users;

import com.community.demo.ApiResponse;
import com.community.demo.auth.Login;
import com.community.demo.users.dto.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<ReadUserResponseDto>> readUser(@Login Long userId) {
        ReadUserResponseDto response = userService.readUser(userId);

        return ResponseEntity
                .ok()
                .body(ApiResponse.of("user_read_success", response));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<UserResponseDto>> createUser(UserRequestDto request) {
        UserResponseDto response = userService.createUser(request);

        return ResponseEntity
                .created(null)
                .body(ApiResponse.of("user_create_success", response));
    }

    @PutMapping("/me")
    public ResponseEntity<ApiResponse<Void>> updateUser(@Login Long userId, UpdateUserRequestDto request) {
        userService.updateUser(userId, request);

        return ResponseEntity
                .ok()
                .body(ApiResponse.of("user_info_update_success", null));
    }

    @PatchMapping("/me/password")
    public ResponseEntity<ApiResponse<Void>> updatePassword(@Login Long userId, UpdatePasswordRequestDto request) {
        userService.updatePassword(userId, request);

        return ResponseEntity
                .ok()
                .body(ApiResponse.of("user_password_update_success", null));
    }

    @DeleteMapping("/me")
    public ResponseEntity<ApiResponse<Void>> deleteUser(@Login Long userId) {
        userService.deleteUser(userId);

        return ResponseEntity
                .ok()
                .body(ApiResponse.of("user_delete_success", null));
    }

    @GetMapping("/dup/email")
    public ResponseEntity<ApiResponse<Void>> isValidEmail(EmailValidationRequestDto dto) {
        userService.isValidEmail(dto.getEmail());

        return ResponseEntity
                .ok()
                .body(ApiResponse.of("email_valid", null));
    }

    @GetMapping("dup/nickname")
    public ResponseEntity<ApiResponse<Void>> isValidNickname(NicknameValidationRequestDto dto) {
        userService.isValidNickname(dto.getNickname());

        return ResponseEntity
                .ok()
                .body(ApiResponse.of("nickname_valid", null));
    }
}
