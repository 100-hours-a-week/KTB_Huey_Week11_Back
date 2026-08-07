package com.community.demo.users.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@AllArgsConstructor
@Getter
public class UpdatePasswordRequestDto {
    private String newPassword;
}
