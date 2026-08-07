package com.community.demo.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.HashMap;
import java.util.Map;

@Getter
public class InvalidFileException extends BusinessException {

    private final Map<String, String> errors;

    public InvalidFileException(String fieldName, String errorMessage) {
        super("invalid_input", HttpStatus.UNPROCESSABLE_CONTENT);

        this.errors = new HashMap<>();
        this.errors.put(fieldName, errorMessage);
    }
}
