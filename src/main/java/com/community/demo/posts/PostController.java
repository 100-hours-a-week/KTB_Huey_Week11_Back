package com.community.demo.posts;

import com.community.demo.posts.dto.controller.*;
import com.community.demo.users.dto.service.DeleteUserServiceRequestDto;
import com.community.demo.users.dto.service.DeleteUserServiceResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping
    public CreatePostControllerResponseDto create(CreatePostControllerRequestDto dto) {
        long userId = 0;
        postService.createPost(dto.toServiceDto(userId));
        return new CreatePostControllerResponseDto();
    }

    @GetMapping
    public ListByPagePostControllerResponseDto listByPage(ListByPagePostControllerRequestDto dto) {
        postService.getPostListByPage()
        return new ListByPagePostControllerResponseDto();
    }

    @GetMapping("/{post_id}")
    public ReadPostControllerResponseDto read(ReadPostControllerRequestDto dto) {
        return new ReadPostControllerResponseDto();
    }

    @PostMapping("/{post_id}")
    public UpdatePostControllerResponseDto update(UpdatePostControllerRequestDto dto) {
        return new UpdatePostControllerResponseDto();
    }

    @PatchMapping("/{post_id}")
    public LikePostControllerResponseDto like(LikePostControllerRequestDto dto) {
        return new LikePostControllerResponseDto();
    }

    @DeleteMapping("/{post_id}")
    public DeleteUserServiceResponseDto delete(DeleteUserServiceRequestDto dto) {
        return new DeleteUserServiceResponseDto();
    }
}
