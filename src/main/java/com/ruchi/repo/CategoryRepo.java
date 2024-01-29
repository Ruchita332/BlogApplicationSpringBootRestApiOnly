package com.ruchi.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ruchi.entity.Category;
import java.util.List;
import com.ruchi.entity.Post;


@Repository
public interface CategoryRepo extends JpaRepository<Category, Long> {
	Optional<Category> findByCategoryName(String categoryName);
}
