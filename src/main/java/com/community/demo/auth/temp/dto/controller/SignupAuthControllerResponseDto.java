package com.community.demo.auth.temp.dto.controller;

import com.community.demo.auth.temp.SignupResponseData;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupAuthControllerResponseDto {
    private String message = "signup_success";
    private SignupResponseData data;
}
