package com.community.demo.posts.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Getter
@Setter
public class PostRequestDto {

    private String title;
    private String content;
    private String imageUrl;
}
