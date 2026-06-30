package com.community.demo.users.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class UpdatePasswordRequestDto {
    private String newPassword;
}
