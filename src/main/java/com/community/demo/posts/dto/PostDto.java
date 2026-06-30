package com.community.demo.posts.dto;

import com.community.demo.posts.entity.Post;
import com.community.demo.posts.entity.PostMetadata;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Getter
public class PostDto {

    private final Boolean isBlinded;
    private final String title;
    private final LocalDateTime postedTime;
    private final Long likes;
    private final Long comments;
    private final Long views;
    private final String userNickname;

    public static PostDto fromEntity(Post post, PostMetadata postMetadata, long count) {
        return new PostDto(
                postMetadata.isBlinded(),
                post.getTitle(),
                postMetadata.getPostedTime(),
                postMetadata.getLikes(),
                count,
                postMetadata.getViews(),
                post.getAuthor().getNickname()
        );
    }
}
