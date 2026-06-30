package com.community.demo.users.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class UpdateNicknameRequestDto {
    private final String newNickname;
}
