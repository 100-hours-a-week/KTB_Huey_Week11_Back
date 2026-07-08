package com.community.demo.comments.dto;

import com.community.demo.comments.Comment;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RequiredArgsConstructor
@Getter
public class CommentResponseDto {
    private final long commentId;
    private final String userNickname;
    private final String userProfileImageUrl;
    private final String postedTime;
    private final String content;

    public static CommentResponseDto fromEntity(Comment comment) {
        return new CommentResponseDto(
                comment.getId(),
                comment.getAuthor().getNickname(),
                comment.getAuthor().getProfileImage().getFilePath(),
                comment.getPostedTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
                comment.getContent());
    }
}
