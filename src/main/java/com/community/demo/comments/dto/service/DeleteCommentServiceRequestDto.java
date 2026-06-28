package com.community.demo.comments.dto.service;

import lombok.Getter;

@Getter
public class DeleteCommentServiceRequestDto {
    private long userId;
    private long postId;
    private long commentId;
}
