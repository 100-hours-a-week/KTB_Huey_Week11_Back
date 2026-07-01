package com.community.demo.users.dto;

import com.community.demo.users.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@AllArgsConstructor
@Getter
public class UserResponseDto {
    private Long userId;

    public static UserResponseDto of(Long userId) {
        return new UserResponseDto(userId);
    }
}
