package com.community.demo.posts;

import com.community.demo.comments.CommentRepository;
import com.community.demo.exception.NotFoundException;
import com.community.demo.posts.dto.*;
import com.community.demo.posts.entity.Post;
import com.community.demo.posts.entity.PostMetadata;
import com.community.demo.users.User;
import com.community.demo.users.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final PostMetadataRepository postMetadataRepository;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;

    @Transactional
    public PostResponseDto createPost(Long userId, PostRequestDto request) {
        User author = userRepository.findById(userId).orElseThrow();
        Post post = new Post(request.getTitle(), author, request.getContent(), request.getImage());
        postRepository.save(post);
        return PostResponseDto.fromEntity(post);
    }

    @Transactional(readOnly = true)
    public ReadPostResponseDto readPost(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow();
        PostMetadata postMetadata = postMetadataRepository.findById(post).orElseThrow();
        postMetadata.view();
        Long count = commentRepository.countByPost(post);
        return ReadPostResponseDto.fromEntity(post, postMetadata, count);
    }

    @Transactional(readOnly = true)
    public ReadPostsByPageResponseDto readPostsByPage(int page) {
        PageRequest pageRequest = PageRequest.of(page, 10, Sort.by("postedTime").descending());
        Page<Post> page = postRepository.findAll(pageRequest);

        return new ReadPostsByPageResponseDto(page.hasNext(), page.map(
                k -> PostDto.fromEntity(
                        k,
                        postMetadataRepository.findById(k).orElseThrow(),
                        commentRepository.countByPost(k)
                )
            ).toList()
        );
    }

    @Transactional
    public void updatePost(Long postId, UpdatePostRequestDto request) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new NotFoundException("not_found"));
        post.update(request.getTitle(), request.getContent(), request.getImage());
        postMetadataRepository.findById(post).orElseThrow().update();
        postRepository.save(post);
    }

    @Transactional
    public void likePost(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow();
        PostMetadata postMetadata = postMetadataRepository.findById(post).orElseThrow(() -> new NotFoundException("not_found"));
        postMetadata.like();
        postMetadataRepository.save(postMetadata);
    }

    @Transactional
    public void deletePost(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow();
        PostMetadata postMetadata = postMetadataRepository.findById(post).orElseThrow(() -> new NotFoundException("not_found"));
        postMetadata.delete();
        postMetadataRepository.save(postMetadata);
    }

    @Transactional
    public void repostPost(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow();
        PostMetadata postMetadata = postMetadataRepository.findById(post).orElseThrow(() -> new NotFoundException("not_found"));
        postMetadata.report();
        postMetadataRepository.save(postMetadata);
    }
}
