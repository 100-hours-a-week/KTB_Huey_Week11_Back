package com.community.demo.comments.dto;

import com.community.demo.comments.Comment;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Getter
public class ReadCommentResponseDto {

    private final long commentId;
    private final String userNickname;
    private final LocalDateTime postedTime;
    private final String content;

    public static ReadCommentResponseDto fromEntity(Comment comment) {
        return new ReadCommentResponseDto(comment.getId(), comment.getAuthor().getNickname(), comment.getPostedTime(), comment.getContent());
    }
}
