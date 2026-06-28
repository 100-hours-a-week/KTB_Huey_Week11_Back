package com.community.demo.comments.dto.service;

import com.community.demo.comments.dto.CommentDto;

import java.util.List;

public class GetCommentsListServiceResponseDto {
    private List<CommentDto> comments;

    public GetCommentsListServiceResponseDto(List<CommentDto> comments) {
        this.comments = comments;
    }
}
