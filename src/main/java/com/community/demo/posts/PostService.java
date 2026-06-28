package com.community.demo.posts;

import com.community.demo.comments.CommentRepository;
import com.community.demo.posts.dto.service.*;
import com.community.demo.posts.entity.Post;
import com.community.demo.posts.entity.PostMetadata;
import com.community.demo.users.User;
import com.community.demo.users.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final PostMetadataRepository postMetadataRepository;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;

    public CreatePostServiceResponseDto createPost(CreatePostServiceRequestDto dto) {
        Post post = new Post(dto.getTitle(), dto.getUserId(), dto.getContent(), dto.getImage());
        Post savedPost = postRepository.save(post);
        PostMetadata postMetadata = new PostMetadata(savedPost.getPostId(), LocalDateTime.now());
        postMetadataRepository.save(postMetadata);
        return new CreatePostServiceResponseDto();
    }

    public DeletePostServiceResponseDto deletePost(DeletePostServiceRequestDto dto) {
        PostMetadata postMetadata = postMetadataRepository.findById(dto.getPostId()).orElseThrow(); //404
        postMetadata.delete();
        postMetadataRepository.save(postMetadata);
        return new DeletePostServiceResponseDto();
    }

    public ViewPostServiceResponseDto viewPost(ViewPostServiceRequestDto dto) {
        Post post = postRepository.findById(dto.getPostId()).orElseThrow();
        PostMetadata postMetadata = postMetadataRepository.findById(post.getPostId()).orElseThrow();
        String userNickname = userRepository.findById(post.getUserId()).orElseThrow().getNickname();
        long comments = commentRepository.countByPostId(post.getPostId());
        return ViewPostServiceResponseDto.fromEntity(post, postMetadata, userNickname, comments);
    }

    public GetListByPageServiceResponseDto getPostListByPage(GetListByPageServiceRequestDto dto) {
        PageRequest pageRequest = PageRequest.of(dto.getPage(), 10);
        Page<Post> page = postRepository.findAll(pageRequest);

        List<PostListUnit> posts = new ArrayList<>();

        for (Post post: page) {
            PostMetadata postMetadata = postMetadataRepository.findById(post.getPostId()).orElseThrow();
            String userNickname = userRepository.findById(post.getUserId()).orElseThrow().getNickname();
            long comments = commentRepository.countByPostId(post.getPostId());
            posts.add(PostListUnit.fromEntity(post, postMetadata, comments, userNickname));
        }

        return new GetListByPageServiceResponseDto(new PostList(page.hasNext(), posts));
    }

    public ModifyPostServiceResponseDto modifyPost(ModifyPostServiceRequestDto dto) {
        Post post = postRepository.findById(dto.getPostId()).orElseThrow();
        post.modify(dto.getModifiedTitle(), dto.getModifiedContent(), dto.getModifiedImage());
        postRepository.save(post);
        return new ModifyPostServiceResponseDto();
    }

    public LikePostServiceResponseDto likePost(LikePostServiceRequestDto dto) {
        PostMetadata postMetadata = postMetadataRepository.findById(dto.getPostId()).orElseThrow();
        postMetadata.like();
        postMetadataRepository.save(postMetadata);
        return new LikePostServiceResponseDto();
    }

    public ReportPostServiceResponseDto reportPost(ReportPostServiceRequestDto dto) {
        PostMetadata postMetadata = postMetadataRepository.findById(dto.getPostId()).orElseThrow();
        postMetadata.report();
        return new ReportPostServiceResponseDto();
    }
}
