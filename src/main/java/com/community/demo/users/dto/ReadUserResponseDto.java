package com.community.demo.users.dto;

import com.community.demo.auth.temp.Auth;
import com.community.demo.users.User;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class ReadUserResponseDto {
    private final String email;
    private final String nickname;
    private final String image;

    public static ReadUserResponseDto fromEntity(User user, Auth auth) {
        return new ReadUserResponseDto(auth.getEmail(), user.getNickname(), user.getProfileImage());
    }
}
