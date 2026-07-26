package com.community.demo.posts;

import com.community.demo.auth.temp.AuthorizedOnly;
import com.community.demo.comments.CommentRepository;
import com.community.demo.exception.BusinessException;
import com.community.demo.exception.NotFoundException;
import com.community.demo.files.File;
import com.community.demo.files.FileRepository;
import com.community.demo.files.FileUtil;
import com.community.demo.posts.dto.*;
import com.community.demo.posts.entity.Post;
import com.community.demo.posts.entity.PostMetadata;
import com.community.demo.users.User;
import com.community.demo.users.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final PostMetadataRepository postMetadataRepository;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final FileRepository fileRepository;

    @Transactional
    public PostResponseDto createPost(Long userId, PostRequestDto request) {
        File postAttachment = resolvePostAttachment(request.getImageUrl());
        log.info(postAttachment.getFilePath());
        User author = getUser(userId);
        Post post = new Post(request.getTitle(), author, request.getContent(), postAttachment);
        Post savedPost = postRepository.save(post);
        PostMetadata postMetadata = new PostMetadata(savedPost);
        postMetadataRepository.save(postMetadata);
        return PostResponseDto.fromEntity(savedPost);
    }

    @Transactional
    public ReadPostResponseDto readPost(Long postId) {

        Post post = getPost(postId);
        PostMetadata postMetadata = getPostMetadata(postId);

        postMetadata.view();
        java.lang.Long count = commentRepository.countByPost(post);
        return ReadPostResponseDto.fromEntity(post, postMetadata, count);
    }

    @Transactional(readOnly = true)
    public ReadPostsByPageResponseDto readPostsByPage(Long page) {
        PageRequest pageRequest = PageRequest.of(page.intValue(), 10, Sort.by("postedTime").descending());
        Page<PostMetadata> validPostMetadata = postMetadataRepository.findByIsDeletedFalse(pageRequest);
        List<PostDto> postList = validPostMetadata.map(p -> PostDto.fromEntity(p.getPost(), p, commentRepository.countByPost(p.getPost()))).toList();

        return new ReadPostsByPageResponseDto(validPostMetadata.hasNext(), postList);
    }

    @PreAuthorize("@postRepository.findById(#postId).get().getAuthor().getEmail() == authentication.principal.username")
    @Transactional
    public void updatePost(Long postId, UpdatePostRequestDto request) {
        Post post = getPost(postId);
        updatePost(post, request);
    }

    @Transactional
    public void likePost(Long postId) {
        Post post = getPost(postId);
        PostMetadata postMetadata = getPostMetadata(postId);

        postMetadata.like();

        postMetadataRepository.save(postMetadata);
    }

    @PreAuthorize("@postRepository.findById(#postId).get().getAuthor().getEmail() == authentication.principal.username")
    @Transactional
    public void deletePost(Long postId) {
        Post post = getPost(postId);
        deletePost(post);
    }

    @Transactional
    public void reportPost(Long postId) {
        Post post = getPost(postId);
        PostMetadata postMetadata = getPostMetadata(postId);

        postMetadata.report();

        postMetadataRepository.save(postMetadata);
    }

    private User getUser(java.lang.Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new NotFoundException("user_not_found"));
    }

    private Post getPost(java.lang.Long postId) {
        return postRepository.findById(postId).orElseThrow(() -> new NotFoundException("post_not_found"));
    }

    private PostMetadata getPostMetadata(Long postId) {
        return postMetadataRepository.findById(postId)
                .orElseThrow(() -> new BusinessException("internal_server_error", HttpStatus.INTERNAL_SERVER_ERROR));
    }

    private void updatePost(Post post, UpdatePostRequestDto request) {
        post.update(request.getTitle(), request.getContent());
        applyPostAttachment(post, request.getImageUrl());

        PostMetadata postMetadata = getPostMetadata(post.getId());
        postMetadata.update();
        postRepository.save(post);
    }

    private void deletePost(Post post) {
        PostMetadata postMetadata = getPostMetadata(post.getId());
        postMetadata.delete();
        postMetadataRepository.save(postMetadata);
    }

    private File resolvePostAttachment(String postAttachmentUrl) {
        log.info("resolvePostAttachment 진입: " + postAttachmentUrl);
        if (postAttachmentUrl == null || postAttachmentUrl.isBlank()) {
            log.info("null");
            return null;
        }

        log.info("not null");
        String relativePath = extractPathFromUrl(postAttachmentUrl);
        log.info("relativePath: " + relativePath);

        return fileRepository.findByFilePath(relativePath)
                .orElseThrow(() -> new NotFoundException("post_attachment_not_found"));
    }

    private String extractPathFromUrl(String url) {
        try {
            URI uri = new URI(url);
            String path = uri.getPath();

            return path != null ? path : url;
        } catch (URISyntaxException exception) {
            return url;
        }
    }

    private void applyPostAttachment(Post post, String requestedPath) {
        if (requestedPath == null) {
            post.updatePostAttachment(null);
            return;
        }

        String relativePath = FileUtil.extractPathFromUrl(requestedPath);

        String currentPath = Optional.ofNullable(post.getPostAttachment())
                .map(File::getFilePath)
                .orElse(null);

        if (relativePath.equals(currentPath)) {
            return;
        }

        File newPostAttachment = fileRepository.findByFilePath(relativePath)
                .orElseThrow(() -> new NotFoundException("post_attachment_not_found"));

        post.updatePostAttachment(newPostAttachment);
    }
}
