package com.community.demo.posts.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class PostRequestDto {

    private String title;
    private String content;
    private String image;
}
