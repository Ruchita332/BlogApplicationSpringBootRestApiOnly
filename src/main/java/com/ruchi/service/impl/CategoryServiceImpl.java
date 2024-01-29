package com.ruchi.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ruchi.entity.Category;
import com.ruchi.entity.Post;
import com.ruchi.entity.Role;
import com.ruchi.entity.User;
import com.ruchi.exceptions.DuplicateItemException;
import com.ruchi.exceptions.ResourceNotFoundException;
import com.ruchi.payloads.CategoryDetailDto;
import com.ruchi.payloads.CategoryDto;
import com.ruchi.payloads.PostDetailDto;
import com.ruchi.repo.CategoryRepo;
import com.ruchi.repo.RoleRepo;
import com.ruchi.repo.UserRepo;
import com.ruchi.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryRepo categoryRepo;
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private RoleRepo roleRepo;
	
	@Override
	public String createCategory(CategoryDto categoryDto, long userId) {
		// TODO Auto-generated method stub
		//verify if the user is admin user
		//Verify User is admin user
		
		String message = "Cannot Create Category";

		User u = userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("Invalid User Id, User is not authorized to make changes"));
		Role admin = roleRepo.findByRoleName("ADMIN").get();
		
		if (u.getRoles().contains(admin)) {

			if (categoryRepo.findByCategoryName(categoryDto.getCategoryName()).isPresent()) {
				throw new DuplicateItemException("The category " + categoryDto.getCategoryName() + " already exists");
			}
			Category cat = new Category();
			BeanUtils.copyProperties(categoryDto, cat);
			Category saveCategory = categoryRepo.save(cat);
			if (saveCategory != null) {
				message = "Category created";
				}
		}else message += "User is not authorized";
		return message;
	}
	

	@Override
	public List<PostDetailDto> viewByCategory(long categoryId) {
		// TODO Auto-generated method stub
		List<Post> posts = categoryRepo.findById(categoryId).get().getPosts();
		
		List<PostDetailDto> postsDto = new ArrayList<>();
		
		for (Post p: posts) {
			PostDetailDto postDetails = new PostDetailDto();
			BeanUtils.copyProperties(p, postDetails);
			postsDto.add(postDetails);
		}
		
		return postsDto;
	}
	


	@Override
	public CategoryDetailDto viewCategoryDetail(Long categoryId) {
		Category category = categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category with " + categoryId + " not found"));

		CategoryDetailDto catDto = new CategoryDetailDto();

		BeanUtils.copyProperties(category, catDto);

		return catDto;
	}
	
	
	@Override
	public String updateCategory(CategoryDto categoryDto, Long categoryId, long userId) {
		// TODO Auto-generated method stub
		
		String message = "Category not updated";
		
		//Check if User is authorized to make changes
		User u = userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("Invalid User Id, User is not authorized to make changes"));
		Role admin = roleRepo.findByRoleName("ADMIN").get();
		
		if (u.getRoles().contains(admin)) {

			Category exists = categoryRepo.findById(categoryId)
					.orElseThrow(() -> new ResourceNotFoundException("Category not found with category id: " + categoryId));
			
			BeanUtils.copyProperties(categoryDto, exists);
			Category save = categoryRepo.save(exists);
			if (save != null) {
				message = "Category Successfully Updated";
			}
		}else message += "User is not authorized";
		
		return message;

	}
	@Override
	public String delete(Long categoryId, long userId) {

		String message = "Category not deleted";
		
		//Check if User is authorized to make changes
		User u = userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("Invalid User Id, User is not authorized to make changes"));
		Role admin = roleRepo.findByRoleName("ADMIN").get();
		
		if (u.getRoles().contains(admin)) {

			categoryRepo.findById(categoryId)
					.orElseThrow(() -> new ResourceNotFoundException("Category not found with category id: " + categoryId));
			categoryRepo.deleteById(categoryId);
			message = "Category Suceessfully deleted";
			
		}else message += "User is not authorized";

		return message;
	}


	@Override
	public List<CategoryDetailDto> viewAllCategories() {
		// TODO Auto-generated method stub
		List<Category> categories = categoryRepo.findAll();
		
		List<CategoryDetailDto> categoryList = new ArrayList<>();
		
		for (Category c: categories) {
			CategoryDetailDto catDto = new CategoryDetailDto();
			BeanUtils.copyProperties(c, catDto);
			categoryList.add(catDto);
		}
		
		return categoryList;
	}



	

	


}
