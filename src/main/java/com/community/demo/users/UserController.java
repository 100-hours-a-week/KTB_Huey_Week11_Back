package com.community.demo.users;

import com.community.demo.ApiResponse;
import com.community.demo.users.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<ReadUserResponseDto>> readUser(Long userId) {
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
    public ResponseEntity<ApiResponse<Void>> updateUser(Long userId, UpdateUserRequestDto request) {
        userService.updateUser(userId, request);

        return ResponseEntity
                .ok()
                .body(ApiResponse.of("user_info_update_success", null));
    }

    @PatchMapping("/me/password")
    public ResponseEntity<ApiResponse<Void>> updatePassword(Long userId, UpdatePasswordRequestDto request) {
        userService.updatePassword(userId, request);

        return ResponseEntity
                .ok()
                .body(ApiResponse.of("user_password_update_success", null));
    }

    @DeleteMapping("/me")
    public ResponseEntity<ApiResponse<Void>> deleteUser(Long userId) {
        userService.deleteUser(userId);

        return ResponseEntity
                .ok()
                .body(ApiResponse.of("user_delete_success", null));
    }
}
