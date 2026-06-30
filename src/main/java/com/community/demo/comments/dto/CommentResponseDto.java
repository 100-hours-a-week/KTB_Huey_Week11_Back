package com.community.demo.comments.dto;

import com.community.demo.comments.Comment;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Getter
public class CommentResponseDto {
    private final long commentId;
    private final String userNickname;
    private final LocalDateTime postedTime;
    private final String content;

    public static CommentResponseDto fromEntity(Comment comment) {
        return new CommentResponseDto(comment.getId(), comment.getAuthor().getNickname(), comment.getPostedTime(), comment.getContent());
    }
}
