package com.community.demo.comments.dto;

import com.community.demo.comments.Comment;
import com.community.demo.time.TimestampUtils;
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
                TimestampUtils.getZonedTime(comment.getPostedTime()),
                comment.getContent());
    }
}
