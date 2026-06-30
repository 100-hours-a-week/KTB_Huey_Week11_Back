package com.community.demo.comments.dto;

import com.community.demo.comments.Comment;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class UpdateCommentResponseDto {

    private final String content;

    public static UpdateCommentResponseDto fromEntity(Comment comment) {
        return new UpdateCommentResponseDto(comment.getContent());
    }
}
