package com.community.demo.exception.handler;

import com.community.demo.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleNotFound(NotFoundException exception) {
        return ResponseEntity
                .status(exception.getStatus())
                .body(ErrorResponseDto.of(exception.getMessage()));
    }

    @ExceptionHandler(BusinessException.class)
    public ErrorResponseDto handleBusiness(BusinessException exception) {
        return ErrorResponseDto.of(exception.getMessage());
    }

    @ExceptionHandler(ForbiddenException.class)
    public ErrorResponseDto handleForbidden(ForbiddenException exception) {
        return ErrorResponseDto.of(exception.getMessage());
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ErrorResponseDto> handleUnauthorized(UnauthorizedException exception) {
        return ResponseEntity
                .status(exception.getStatus())
                .body(ErrorResponseDto.of(exception.getMessage()));
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResponseDto> handleBadRequest(BadRequestException exception) {
        return ResponseEntity
                .status(exception.getStatus())
                .body(ErrorResponseDto.of(exception.getMessage()));
    }
}
