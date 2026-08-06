package com.community.demo.posts.dto;

import com.community.demo.posts.entity.Post;
import com.community.demo.posts.entity.PostMetadata;
import com.community.demo.time.TimestampUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@AllArgsConstructor
@Getter
public class PostDto {

    private Boolean isBlinded;
    private Long postId;
    private String title;
    private String postedTime;
    private Long likes;
    private Long comments;
    private Long views;
    private String userNickname;
    private String userProfileImageUrl;

    public static PostDto fromEntity(Post post, PostMetadata postMetadata, long count) {
        return new PostDto(
                postMetadata.isBlinded(),
                post.getId(),
                post.getTitle(),
                TimestampUtils.getZonedTime(postMetadata.getPostedTime()),
                postMetadata.getLikes(),
                count,
                postMetadata.getViews(),
                post.getAuthor().getNickname(),
                post.getAuthor().getProfileImage().getFilePath()
        );
    }
}
