package com.community.demo.comments.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@AllArgsConstructor
@Getter
public class UpdateCommentRequestDto {

    private String content;
}
