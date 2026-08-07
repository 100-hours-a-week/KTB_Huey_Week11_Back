package com.community.demo.users.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class NicknameValidationForUpdateRequestDto {
    private String newNickname;
}
