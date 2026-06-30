package com.community.demo.posts.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class UpdatePostRequestDto {
    private final String title;
    private final String content;
    private final String image;
}
