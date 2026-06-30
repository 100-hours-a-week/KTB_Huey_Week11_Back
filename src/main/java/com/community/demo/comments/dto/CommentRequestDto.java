package com.community.demo.comments.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class CommentRequestDto {

    private final String content;
}
