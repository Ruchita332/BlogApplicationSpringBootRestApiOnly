package com.ruchi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ruchi.payloads.CategoryDetailDto;
import com.ruchi.payloads.CategoryDto;
import com.ruchi.payloads.PostDetailDto;
import com.ruchi.service.CategoryService;

import javax.validation.Valid;

@RestController
@RequestMapping ("/blog/user/{userId}")
@CrossOrigin(origins ="*")
public class CategoryController {
	
	@Autowired
	private CategoryService catService;
	
	//Handling CATEGORY request
	@GetMapping("/categories")
	public ResponseEntity<List<CategoryDetailDto>> viewAllCategories () {
		List<CategoryDetailDto> categoryList = catService.viewAllCategories();
		
	return ResponseEntity.ok().body(categoryList);
	}
	
	@GetMapping("/category/{catId}/posts")
	public ResponseEntity<List<PostDetailDto>> ViewPostsByCategory (@PathVariable long catId) {
		List<PostDetailDto> postsByCategory = catService.viewByCategory(catId);
		
	return ResponseEntity.ok().body(postsByCategory);
	}
	
	@GetMapping("/category/{catId}")
	public ResponseEntity <CategoryDetailDto> viewCategoryDetail (@PathVariable long catId) {
		CategoryDetailDto categoryInfo = catService.viewCategoryDetail(catId);
		
	return ResponseEntity.ok().body(categoryInfo);
	}
	
	@PostMapping("/category/create")
	public ResponseEntity<String> createCategory(@Valid @RequestBody CategoryDto categoryDto, @PathVariable  long userId) {
		String created = catService.createCategory(categoryDto, userId);
		
	return ResponseEntity.created(null).body(created);
	}
	
	@PutMapping("/category/{catId}/update")
//	http://localhost:3000/blog/user/1/category/1/update
	public ResponseEntity<String> updateCategory  (@RequestBody CategoryDto categoryDto, @PathVariable long userId, @PathVariable long catId) {
		String updated = catService.updateCategory(categoryDto, catId, userId);
		
	return ResponseEntity.ok().body(updated);
	}
	
	@DeleteMapping("category/{catId}/delete")
	public ResponseEntity<String> deleteComment  (@PathVariable long userId, @PathVariable long catId) {
		String deleted = catService.delete(catId, userId);
		
	return ResponseEntity.ok().body(deleted);
	}
	
	
}
