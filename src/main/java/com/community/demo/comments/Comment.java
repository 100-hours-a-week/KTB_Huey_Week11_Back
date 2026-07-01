package com.community.demo.comments;

import com.community.demo.posts.entity.Post;
import com.community.demo.users.User;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Getter
public class Comment {
    @Id @GeneratedValue
    @Column(name = "comment_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User author;

    private LocalDateTime postedTime;
    private String content;
    private boolean isDeleted;

    public Comment() {
    }

    public Comment(Post post, User user, String content) {
        this.post = post;
        this.author = user;
        this.postedTime = LocalDateTime.now();
        this.content = content;
        this.isDeleted = false;
        post.getComments().add(this);
    }

    public void delete() {
        this.isDeleted = true;
    }

    public void modify(String content) {
        this.content = content;
    }
}
