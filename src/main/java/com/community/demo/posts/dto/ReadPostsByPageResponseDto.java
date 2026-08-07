package com.community.demo.posts.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
@Getter
public class ReadPostsByPageResponseDto {

    private final boolean hasNext;
    private final List<PostDto> posts;
}
