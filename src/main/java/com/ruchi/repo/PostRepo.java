package com.ruchi.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ruchi.entity.Category;
import com.ruchi.entity.Post;
import com.ruchi.entity.User;

import java.util.List;
import java.util.Optional;


@Repository
public interface PostRepo extends JpaRepository<Post, Long> {
	Optional<Post> findByCategory(Category category);
	Optional<Post> findByUser(User user);
}
