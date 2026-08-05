package com.community.demo.session;

import com.community.demo.users.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserInfoDto {
    private Long id;
    private String email;
    private String nickname;
    private String profileImageUrl;

    public static UserInfoDto from(User user) {
        return new UserInfoDto(user.getId(), user.getEmail(), user.getNickname(), user.getProfileImage().getFilePath());
    }
}
