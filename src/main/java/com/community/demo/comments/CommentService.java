package com.community.demo.comments;

import com.community.demo.comments.dto.CommentDto;
import com.community.demo.comments.dto.service.*;
import com.community.demo.users.User;
import com.community.demo.users.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;

    public GetCommentsListServiceResponseDto getCommentsList(GetCommentsListServiceRequestDto dto) {
        List<Comment> comments = commentRepository.findAllByPostId(dto.getPostId());
        List<CommentDto> commentDtos = new ArrayList<>();
        for (Comment comment: comments) {
            User user = userRepository.findById(comment.getUserId()).orElseThrow();
            commentDtos.add(CommentDto.fromEntity(comment, user.getNickname()));
        }
        return new GetCommentsListServiceResponseDto(commentDtos);
    }

    public ModifyCommentServiceResponseDto modifyComment(ModifyCommentServiceRequestDto dto) {
        Comment comment = commentRepository.findById(dto.getCommentId()).orElseThrow();
        comment.modify();
        commentRepository.save(comment);
        return new ModifyCommentServiceResponseDto();
    }

    public CreateCommentServiceResponseDto createComment(CreateCommentServiceRequestDto dto) {
        Comment comment = new Comment(dto.getPostId(), dto.getUserId(), LocalDateTime.now(), dto.getContent());
        commentRepository.save(comment);
        return new CreateCommentServiceResponseDto();
    }

    public DeleteCommentServiceResponseDto deleteComment(DeleteCommentServiceRequestDto dto) {
        Comment comment = commentRepository.findById(dto.getCommentId()).orElseThrow();
        comment.delete();
        commentRepository.save(comment);
        return new DeleteCommentServiceResponseDto();
    }

}
