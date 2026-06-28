package com.community.demo.comments;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Getter
public class Comment {
    @Id @GeneratedValue
    private long id;
    private long postId;
    private long userId;
    private LocalDateTime postedTime;
    private String content;

    public Comment() {
    }

    public Comment(long postId, long userId, LocalDateTime postedTime, String content) {
        this.postId = postId;
        this.userId = userId;
        this.postedTime = postedTime;
        this.content = content;
    }

    public void delete() {

    }

    public void modify() {

    }
}
