package com.community.demo.posts.dto;

import com.community.demo.posts.entity.Post;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class PostResponseDto {
    private final java.lang.Long postId;

    public static PostResponseDto fromEntity(Post post) {
        return new PostResponseDto(post.getId());
    }
}
