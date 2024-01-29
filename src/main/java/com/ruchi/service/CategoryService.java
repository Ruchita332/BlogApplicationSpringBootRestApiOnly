package com.ruchi.service;

import java.util.List;

import com.ruchi.payloads.CategoryDetailDto;
import com.ruchi.payloads.CategoryDto;
import com.ruchi.payloads.PostDetailDto;

public interface CategoryService {
	public List<CategoryDetailDto> viewAllCategories ();
	
	public List<PostDetailDto> viewByCategory(long categoryId);
	
	public String createCategory (CategoryDto categoryDto, long userId);
	
	public CategoryDetailDto viewCategoryDetail(Long categoryId);
	
	public String updateCategory (CategoryDto categoryDto, Long categoryId, long userId);
	
	public String delete(Long categoryId, long userId);

}

