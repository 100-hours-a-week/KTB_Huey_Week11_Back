package com.community.demo.posts.dto;

import com.community.demo.posts.entity.Post;
import com.community.demo.posts.entity.PostMetadata;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Getter
public class ReadPostResponseDto {

    private final String title;
    private final String userNickname;
    private final LocalDateTime postedTime;
    private final String content;
    private final String image;
    private final Long likes;
    private final Long views;
    private final Long comments;
    private final boolean edited;

    public static ReadPostResponseDto fromEntity(Post post, PostMetadata postMetadata, long count) {
        return new ReadPostResponseDto(
                post.getTitle(),
                post.getAuthor().getNickname(),
                postMetadata.getPostedTime(),
                post.getContent(),
                post.getImage(),
                postMetadata.getLikes(),
                postMetadata.getViews(),
                count,
                postMetadata.isEdited()
                );
    }
}
