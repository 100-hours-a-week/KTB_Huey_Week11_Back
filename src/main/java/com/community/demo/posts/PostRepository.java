package com.community.demo.posts;

import com.community.demo.posts.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, java.lang.Long> {
    Page<Post> findAll(Pageable pageable);
}
