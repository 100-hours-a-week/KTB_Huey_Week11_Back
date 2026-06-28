package com.community.demo.posts;

import com.community.demo.posts.entity.Post;
import com.community.demo.posts.entity.PostMetadata;
import com.community.demo.users.User;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class PostListUnit {
    private boolean isBlinded;
    private String title;
    private LocalDateTime postedTime;
    private long likes;
    private long comments;
    private long views;
    private String userNickname;

    public PostListUnit(boolean isBlinded, String title, LocalDateTime postedTime, long likes, long comments, long views, String userNickname) {
        this.isBlinded = isBlinded;
        this.title = title;
        this.postedTime = postedTime;
        this.likes = likes;
        this.comments = comments;
        this.views = views;
        this.userNickname = userNickname;
    }

    public static PostListUnit fromEntity(Post post, PostMetadata postMetadata, long comments, String userNickname) {
        return new PostListUnit(postMetadata.isBlinded(), post.getTitle(), postMetadata.getPostedTime(), postMetadata.getLikes(), comments, postMetadata.getViews(), userNickname);
    }
}
