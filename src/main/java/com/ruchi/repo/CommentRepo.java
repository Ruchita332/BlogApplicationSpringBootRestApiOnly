package com.ruchi.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ruchi.entity.Comment;
import com.ruchi.entity.Post;
import com.ruchi.entity.User;

@Repository
public interface CommentRepo extends JpaRepository<Comment, Long> {
	Optional<Comment> findByPost(Post post);
	Optional<Comment> findByUser(User user);
}
