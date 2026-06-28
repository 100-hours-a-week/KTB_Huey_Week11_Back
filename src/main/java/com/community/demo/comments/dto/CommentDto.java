package com.community.demo.comments.dto;

import com.community.demo.comments.Comment;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
public class CommentDto {
    private long commentId;
    private String userNickname;
    private LocalDateTime postedTime;
    private String content;

    public CommentDto(long commentId, String userNickname, LocalDateTime postedTime, String content) {
        this.commentId = commentId;
        this.userNickname = userNickname;
        this.postedTime = postedTime;
        this.content = content;
    }

    public static CommentDto fromEntity(Comment comment, String userNickname) {
        return new CommentDto(comment.getId(), userNickname, comment.getPostedTime(), comment.getContent());
    }
}
