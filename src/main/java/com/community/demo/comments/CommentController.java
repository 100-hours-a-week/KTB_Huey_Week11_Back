package com.community.demo.comments;

import com.community.demo.ApiResponse;
import com.community.demo.auth.security.SecurityUtils;
import com.community.demo.auth.temp.Login;
import com.community.demo.comments.dto.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/posts/{postId}/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<ApiResponse<CommentResponseDto>> createComment(@PathVariable Long postId, Authentication authentication, CommentRequestDto request) {
        Long userId = SecurityUtils.resolveAuthentication(authentication);
        CommentResponseDto response = commentService.createComment(postId, userId, request);

        return ResponseEntity
                .created(null)
                .body(ApiResponse.of("comment_create_success", response));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<ReadCommentResponseDto>>> readAllComments(@PathVariable Long postId) {
        List<ReadCommentResponseDto> response = commentService.readAllComments(postId);

        return ResponseEntity
                .ok()
                .body(ApiResponse.of("comment_read_all_success", response));
    }

    @PatchMapping
    public ResponseEntity<ApiResponse<UpdateCommentResponseDto>> updateComment(@RequestParam Long commentId, UpdateCommentRequestDto request) {
        log.info(request.getContent());
        UpdateCommentResponseDto response = commentService.updateComment(commentId, request);

        return ResponseEntity
                .ok()
                .body(ApiResponse.of("comment_modify_success", response));
    }

    @DeleteMapping
    public ResponseEntity<ApiResponse<Void>> deleteComment(@RequestParam Long commentId) {
        commentService.deleteComment(commentId);

        return ResponseEntity
                .ok()
                .body(ApiResponse.of("comment_delete_success", null));
    }
}
