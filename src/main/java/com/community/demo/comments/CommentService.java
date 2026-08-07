package com.community.demo.comments;

import com.community.demo.comments.dto.*;
import com.community.demo.exception.NotFoundException;
import com.community.demo.auth.temp.AuthorizedOnly;
import com.community.demo.posts.PostRepository;
import com.community.demo.posts.entity.Post;
import com.community.demo.users.User;
import com.community.demo.users.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    @Transactional(readOnly = true)
    public List<ReadCommentResponseDto> readAllComments(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new NotFoundException("not_found"));
        List<Comment> comments = commentRepository.findAllByIsDeletedFalseAndPost(post);
        return comments.stream().map(ReadCommentResponseDto::fromEntity).toList();
    }

    @PreAuthorize("@commentRepository.findById(#commentId).get().getAuthor().getEmail() == authentication.principal.username")
    @Transactional
    public UpdateCommentResponseDto updateComment(Long commentId, UpdateCommentRequestDto request) {
        Comment comment = getComment(commentId);

        Comment updatedComment = updateComment(comment, request);

        return UpdateCommentResponseDto.fromEntity(updatedComment);
    }

    @Transactional
    public CommentResponseDto createComment(Long postId, Long userId, CommentRequestDto request) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new NotFoundException("not_found"));
        User author = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("not_found"));

        Comment comment = new Comment(post, author, request.getContent());
        commentRepository.save(comment);

        return CommentResponseDto.fromEntity(comment);
    }

    @PreAuthorize("@commentRepository.findById(#commentId).get().getAuthor().getEmail() == authentication.principal.username")
    @Transactional
    public void deleteComment(Long commentId) {
        Comment comment = getComment(commentId);

        deleteComment(comment);
    }

    private Comment getComment(Long commentId) {
        return commentRepository.findById(commentId).orElseThrow(() -> new NotFoundException("comment_not_found"));
    }

    private Comment updateComment(Comment comment, UpdateCommentRequestDto request) {
        log.info(request.getContent());
        comment.modify(request.getContent());
        return commentRepository.save(comment);
    }

    private void deleteComment(Comment comment) {
        comment.delete();
        commentRepository.save(comment);
    }
}
