package com.community.demo.posts.dto.controller;

import com.community.demo.posts.dto.service.CreatePostServiceRequestDto;
import lombok.Setter;

@Setter
public class CreatePostControllerRequestDto {
    private String title;
    private String content;
    private String image;

    public CreatePostControllerRequestDto(String title, String content, String image) {
        this.title = title;
        this.content = content;
        this.image = image;
    }

    public CreatePostServiceRequestDto toServiceDto(long userId) {
        return new CreatePostServiceRequestDto(title, userId, content, image);
    }
}
