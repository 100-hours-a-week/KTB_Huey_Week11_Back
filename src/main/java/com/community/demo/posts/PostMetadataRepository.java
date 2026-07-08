package com.community.demo.posts;

import com.community.demo.posts.entity.PostMetadata;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostMetadataRepository extends JpaRepository<PostMetadata, Long> {
    Page<PostMetadata> findByIsDeletedFalse(Pageable pageable);
}
