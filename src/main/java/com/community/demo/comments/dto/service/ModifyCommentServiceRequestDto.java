package com.community.demo.comments.dto.service;

import lombok.Getter;

@Getter
public class ModifyCommentServiceRequestDto {
    private long userId;
    private long postId;
    private long commentId;
    private String content;
}
