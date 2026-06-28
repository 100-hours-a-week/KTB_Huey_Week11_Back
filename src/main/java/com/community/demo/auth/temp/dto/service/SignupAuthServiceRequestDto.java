package com.community.demo.auth.temp.dto.service;

import lombok.Getter;

@Getter
public class SignupAuthServiceRequestDto {
    private long userId;
    private String email;
    private String password;
}
