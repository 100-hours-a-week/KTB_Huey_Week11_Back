package com.community.demo.users.dto;

import com.community.demo.users.User;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class UserResponseDto {
    private final long userId;

    public static UserResponseDto fromEntity(User user) {
        return new UserResponseDto(user.getId());
    }
}
