package com.community.demo.comments.dto.service;

import lombok.Getter;

@Getter
public class CreateCommentServiceRequestDto {
    private long userId;
    private long postId;
    private String content;
}
