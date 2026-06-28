package com.community.demo.auth.temp.dto.service;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginServiceResponseDto {
    public boolean isLoginSuccessful;
    public long user_id;

    public LoginServiceResponseDto(boolean isLoginSuccessful) {
        this.isLoginSuccessful = isLoginSuccessful;
    }
}
