package com.community.demo.posts;

import com.community.demo.ApiResponse;
import com.community.demo.auth.Login;
import com.community.demo.posts.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping
    public ResponseEntity<ApiResponse<PostResponseDto>> createPost(@Login Long userId, PostRequestDto request) throws URISyntaxException {
        PostResponseDto response = postService.createPost(userId, request);

        return ResponseEntity
                .created(new URI("/posts/" + response.getPostId()))
                .body(ApiResponse.of("post_success", response));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<ReadPostsByPageResponseDto>> readPostsByPage(@RequestParam Long page) {
        ReadPostsByPageResponseDto response = postService.readPostsByPage(page);

        return ResponseEntity
                .ok()
                .body(ApiResponse.of("posts_view_success", response));
    }

    @GetMapping("/view")
    public ResponseEntity<ApiResponse<ReadPostResponseDto>> readPost(@RequestParam Long postId) {
        ReadPostResponseDto response = postService.readPost(postId);

        return ResponseEntity
                .ok()
                .body(ApiResponse.of("post_view_success", response));
    }

    @PatchMapping("/{postId}")
    public ResponseEntity<ApiResponse<Void>> updatePost(@PathVariable Long postId, UpdatePostRequestDto request) {
        postService.updatePost(postId, request);

        return ResponseEntity
                .ok()
                .body(ApiResponse.of("post_update_success", null));
    }

    @PatchMapping("/{postId}/likes")
    public ResponseEntity<ApiResponse<Void>> like(@PathVariable Long postId) {
        postService.likePost(postId);

        return ResponseEntity
                .ok()
                .body(ApiResponse.of("post_like_success", null));
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long postId) {
        postService.deletePost(postId);

        return ResponseEntity
                .ok()
                .body(ApiResponse.of("post_delete_success", null));
    }

    @PatchMapping("/{postId}/reports")
    public ResponseEntity<ApiResponse<Void>> report(@PathVariable Long postId) {
        postService.reportPost(postId);

        return ResponseEntity
                .ok()
                .body(ApiResponse.of("post_report_success", null));
    }
}
