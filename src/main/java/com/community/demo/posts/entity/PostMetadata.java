package com.community.demo.posts.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Getter
public class PostMetadata {
    @Id
    private long postId;
    private LocalDateTime postedTime;
    private long likes;
    private long views;
    private boolean isDeleted;
    private long reported;
    private boolean isBlinded;

    public void delete() {
        this.isDeleted = true;
    }

    public PostMetadata() {

    }

    public PostMetadata(long postId, LocalDateTime postedTime) {
        this.postId = postId;
        this.postedTime = postedTime;
        this.likes = 0;
        this.views = 0;
        this.isDeleted = false;
        this.isBlinded = true;
    }

    public void like() {
        this.likes++;
    }

    public void view() {
        this.views++;
    }

    public void report() {
        this.reported++;
        if (reported >= 5) {
            isBlinded = false;
        }
    }
}
