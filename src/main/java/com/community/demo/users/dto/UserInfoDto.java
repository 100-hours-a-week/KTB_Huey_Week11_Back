package com.community.demo.users.dto;

import com.community.demo.users.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserInfoDto {
    private Long userId;
    private String email;
    private String nickname;
    private String profileImage;

    public static UserInfoDto from(User user) {
        return new UserInfoDto(user.getId(), user.getEmail(), user.getNickname(), user.getProfileImage().getFilePath());
    }
}
