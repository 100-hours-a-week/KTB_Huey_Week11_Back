package com.community.demo.auth.temp.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginResponseDto {

    private String userId;
    private String email;
    private String nickname;
    private String profileImage;

    public static LoginResponseDto of(String userId, String email, String nickname, String profileImage) {
        return new LoginResponseDto(userId, email, nickname, profileImage);
    }
}
