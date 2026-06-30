package com.community.demo.exception.handler;

import com.community.demo.exception.*;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ErrorResponseDto handleNotFound(NotFoundException exception) {
        return ErrorResponseDto.of(exception.getMessage());
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
    public ErrorResponseDto handleUnauthorized(UnauthorizedException exception) {
        return ErrorResponseDto.of(exception.getMessage());
    }

    @ExceptionHandler(BadRequestException.class)
    public ErrorResponseDto handleBadRequest(BadRequestException exception) {
        return ErrorResponseDto.of(exception.getMessage());
    }
}
