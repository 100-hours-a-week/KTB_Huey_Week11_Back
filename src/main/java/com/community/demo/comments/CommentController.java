package com.community.demo.comments;

import com.community.demo.ApiResponse;
import com.community.demo.comments.dto.*;
import com.community.demo.comments.dto.controller.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/posts/{postId}/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<ApiResponse<CommentResponseDto>> createComment(@PathVariable Long postId, CommentRequestDto request) {
        Long userId = 0L;

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
    public ResponseEntity<ApiResponse<UpdateCommentResponseDto>> updateComment(@PathVariable Long postId, @RequestParam Long commentId, UpdateCommentRequestDto request) {
        commentService.updateComment(postId, commentId, request);

        return ResponseEntity
                .ok()
                .body(ApiResponse.of("comment_modify_success", null));
    }

    @DeleteMapping
    public ResponseEntity<ApiResponse<Void>> deleteComment(@PathVariable Long postId, @RequestParam Long commentId) {
        commentService.deleteComment(postId, commentId);

        return ResponseEntity
                .ok()
                .body(ApiResponse.of("comment_delete_success", null));
    }
}
