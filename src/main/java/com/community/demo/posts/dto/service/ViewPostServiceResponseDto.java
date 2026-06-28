package com.community.demo.posts.dto.service;

import com.community.demo.posts.entity.Post;
import com.community.demo.posts.entity.PostMetadata;

import java.time.LocalDateTime;

public class ViewPostServiceResponseDto {
    private String title;
    private String userNickname;
    private LocalDateTime postedTime;
    private String content;
    private String image;
    private long likes;
    private long views;
    private long comments;

    public ViewPostServiceResponseDto(String title, String userNickname, LocalDateTime postedTime, String content, String image, long likes, long views, long comments) {
        this.title = title;
        this.userNickname = userNickname;
        this.postedTime = postedTime;
        this.content = content;
        this.image = image;
        this.likes = likes;
        this.views = views;
        this.comments = comments;
    }

    public static ViewPostServiceResponseDto fromEntity(Post post, PostMetadata postMetadata, String userNickname, long comments) {
        return new ViewPostServiceResponseDto(
                post.getTitle(),
                userNickname,
                postMetadata.getPostedTime(),
                post.getContent(),
                post.getImage(),
                postMetadata.getLikes(),
                postMetadata.getViews(),
                comments
        );
    }
}
