package com.community.demo.posts.dto.service;

import lombok.Data;

@Data
public class CreatePostServiceRequestDto {
    public String title;
    public long userId;
    public String content;
    public String image;

    public CreatePostServiceRequestDto(String title, long userId, String content, String image) {
        this.title = title;
        this.userId = userId;
        this.content = content;
        this.image = image;
    }
}
