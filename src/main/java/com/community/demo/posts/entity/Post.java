package com.community.demo.posts.entity;

import com.community.demo.comments.Comment;
import com.community.demo.files.File;
import com.community.demo.users.User;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "post")
public class Post {

    @Id @GeneratedValue
    @Column(name = "post_id")
    private java.lang.Long id;

    private String title;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User author;

    private String content;

    @OneToOne
    @JoinColumn(name = "file_id")
    private File postAttachment;

    @OneToMany(mappedBy = "post")
    private List<Comment> comments = new ArrayList<>();

    protected Post() {

    }

    public Post(String title, User author, String content, File postAttachment) {
        this.title = title;
        this.author = author;
        this.content = content;
        this.postAttachment = postAttachment;
    }

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public void updatePostAttachment(File postAttachment) {
        this.postAttachment = postAttachment;
    }
}
