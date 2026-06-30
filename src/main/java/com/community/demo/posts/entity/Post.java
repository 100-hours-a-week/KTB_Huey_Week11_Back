package com.community.demo.posts.entity;

import com.community.demo.users.User;
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
    private User author;
    private String content;
    private String image;

    protected Post() {

    }

    public Post(String title, User author, String content, String image) {
        this.title = title;
        this.author = author;
        this.content = content;
        this.image = image;
    }

    public void update(String title, String content, String image) {
        this.title = title;
        this.content = content;
        this.image = image;
    }
}
