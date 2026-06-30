package com.community.demo.comments;

import com.community.demo.comments.dto.*;
import com.community.demo.exception.NotFoundException;
import com.community.demo.posts.PostRepository;
import com.community.demo.posts.entity.Post;
import com.community.demo.users.User;
import com.community.demo.users.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    @Transactional(readOnly = true)
    public List<ReadCommentResponseDto> readAllComments(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new NotFoundException("not_found"));
        List<Comment> comments = commentRepository.findAllByPost(post);
        List<ReadCommentResponseDto> dtos = comments.stream().map(ReadCommentResponseDto::fromEntity).toList();
        return dtos;
    }

    @Transactional
    public UpdateCommentResponseDto updateComment(Long postId, Long commentId, UpdateCommentRequestDto request) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new NotFoundException("not_found"));
        Comment comment = commentRepository.findByIdAndPost(commentId, post).orElseThrow();

        comment.modify(request.getContent());
        commentRepository.save(comment);

        return UpdateCommentResponseDto.fromEntity(comment);
    }

    @Transactional
    public CommentResponseDto createComment(Long postId, Long userId, CommentRequestDto request) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new NotFoundException("not_found"));
        User author = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("not_found"));

        Comment comment = new Comment(post, author, request.getContent());
        commentRepository.save(comment);

        return CommentResponseDto.fromEntity(comment);
    }

    @Transactional
    public void deleteComment(Long postId, Long commentId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new NotFoundException("not_found"));
        Comment comment = commentRepository.findByIdAndPost(commentId, post).orElseThrow();

        comment.delete();
        commentRepository.save(comment);
    }

}
