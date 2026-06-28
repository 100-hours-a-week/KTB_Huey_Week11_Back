package com.community.demo.posts.dto.service;

import com.community.demo.posts.PostList;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetListByPageServiceResponseDto {
    private String message = "posts_view_success";
    private PostList posts;

    public GetListByPageServiceResponseDto(PostList posts) {
        this.posts = posts;
    }
}
