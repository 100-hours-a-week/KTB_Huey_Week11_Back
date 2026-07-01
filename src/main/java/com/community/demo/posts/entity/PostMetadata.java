package com.community.demo.posts.entity;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Getter
public class PostMetadata {
    @Id
    private Long id;
    @OneToOne
    @MapsId
    @JoinColumn(name = "post_id")
    private Post post;
    private LocalDateTime postedTime;
    private long likes;
    private long views;
    private boolean isDeleted;
    private long reported;
    private boolean isBlinded;
    private boolean isEdited;

    public void delete() {
        this.isDeleted = true;
    }

    protected PostMetadata() {

    }

    public PostMetadata(Post post) {
        this.post = post;
        this.postedTime = LocalDateTime.now();
        this.likes = 0L;
        this.views = 0L;
        this.isDeleted = false;
        this.reported = 0L;
        this.isBlinded = false;
        this.isEdited = false;
    }

    public void like() {
        this.likes++;
    }

    public void view() {
        this.views++;
    }

    public void update() {
        this.isEdited = true;
    }

    public void report() {
        this.reported++;
        if (reported >= 5) {
            isBlinded = false;
        }
    }
}
