package com.community.demo.posts.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;

@Entity
@Getter
public class Post {

    @Id @GeneratedValue
    private long postId;
    private String title;
    private long userId;
    private String content;
    private String image;
    private static long autoIncrement = 1;

    public Post(String title, long userId, String content, String image) {
        this.title = title;
        this.userId = userId;
        this.content = content;
        this.image = image;
        this.postId = autoIncrement;
        autoIncrement++;
    }

    public void modify(String title, String content, String image) {
        this.title = title;
        this.content = content;
        this.image = image;
    }

    public Post() {

    }

}
