package com.community.demo.users.dto;

import com.community.demo.auth.temp.Auth;
import com.community.demo.users.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReadUserResponseDto {
    private String email;
    private String nickname;
    private String imageUrl;

    public static ReadUserResponseDto fromEntity(User user) {
        return new ReadUserResponseDto(user.getEmail(), user.getNickname(), user.getProfileImage().getFilePath());
    }
}
