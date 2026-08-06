package com.community.demo.comments.dto;

import com.community.demo.comments.Comment;
import com.community.demo.time.TimestampUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
public class CommentDto {
    private long commentId;
    private String userNickname;
    private String postedTime;
    private String content;

    public static CommentDto fromEntity(Comment comment, String userNickname) {
        return new CommentDto(comment.getId(), userNickname, TimestampUtils.getZonedTime(comment.getPostedTime()), comment.getContent());
    }
}
