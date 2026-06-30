package com.community.demo.posts;

import com.community.demo.posts.entity.Post;
import com.community.demo.posts.entity.PostMetadata;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PostMetadataRepository extends JpaRepository<PostMetadata, Post> {
}
