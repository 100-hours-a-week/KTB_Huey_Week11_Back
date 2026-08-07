package com.community.demo.comments;

import com.community.demo.posts.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment, java.lang.Long> {
    public List<Comment> findAllByPost(Post post);

    public Optional<Comment> findByIdAndPost(long commentId, Post post);

    public Long countByPost(Post post);

    public List<Comment> findAllByIsDeletedFalseAndPost(Post post);
}
