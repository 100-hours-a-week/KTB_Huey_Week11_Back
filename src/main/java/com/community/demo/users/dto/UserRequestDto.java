package com.community.demo.users.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class UserRequestDto {

    private final String email;
    private final String password;
    private final String nickname;
    private final String profileImage;
}
