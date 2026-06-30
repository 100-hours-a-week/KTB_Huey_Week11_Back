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

    @GetMapping
    public ResponseEntity<ApiResponse<ReadUserResponseDto>> readUser(@RequestParam Long userId) {
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

    @PatchMapping("/{userId}/password")
    public ResponseEntity<ApiResponse<Void>> updatePassword(@PathVariable Long userId, UpdatePasswordRequestDto request) {
        userService.updatePassword(userId, request);

        return ResponseEntity
                .ok()
                .body(ApiResponse.of("user_password_update_success", null));
    }

    @PatchMapping("/{userId}/nickname")
    public ResponseEntity<ApiResponse<Void>> updateNickname(@PathVariable Long userId, UpdateNicknameRequestDto request) {
        userService.updateNickname(userId, request);

        return ResponseEntity
                .ok()
                .body(ApiResponse.of("user_nickname_update_success", null));
    }

    @DeleteMapping
    public ResponseEntity<ApiResponse<Void>> deleteUser(@RequestParam Long userId) {
        userService.deleteUser(userId);

        return ResponseEntity
                .ok()
                .body(ApiResponse.of("user_delete_success", null));
    }
}
