package com.community.demo.exception;

import lombok.Getter;

@Getter
public class ErrorResponseDto {

    private final String message;
    private final Object data;

    private ErrorResponseDto(String message) {
        this.message = message;
        this.data = null;
    }

    public static ErrorResponseDto of(String message) {
        return new ErrorResponseDto(message);
    }
}
