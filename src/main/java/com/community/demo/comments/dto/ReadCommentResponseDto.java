package com.community.demo.comments.dto;

import com.community.demo.comments.Comment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@AllArgsConstructor
@Getter
public class ReadCommentResponseDto {

    private Long commentId;
    private String userNickname;
    private String userProfileImageUrl;
    private String postedTime;
    private String content;

    public static ReadCommentResponseDto fromEntity(Comment comment) {
        return new ReadCommentResponseDto(
                comment.getId(),
                comment.getAuthor().getNickname(),
                comment.getAuthor().getProfileImage().getFilePath(),
                comment.getPostedTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
                comment.getContent());
    }
}
