package com.community.demo.auth.temp.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Getter
@Setter
public class LoginRequestDto {
    private final String email;
    private final String password;
}
