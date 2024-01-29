package com.ruchi.service.impl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.beans.BeanCopier.Generator;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ruchi.entity.Category;
import com.ruchi.entity.Post;
import com.ruchi.entity.User;
import com.ruchi.exceptions.ResourceNotFoundException;
import com.ruchi.payloads.PostDetailDto;
import com.ruchi.payloads.createPostDto;
import com.ruchi.repo.CategoryRepo;
import com.ruchi.repo.PostRepo;
import com.ruchi.repo.UserRepo;
import com.ruchi.service.PostService;
import com.ruchi.utils.ImageNameGenerator;
import com.ruchi.utils.Imagefile;


@Service
public class PostServiceImpl implements PostService {
	
	private final String DB_PATH = "/Users/ruchitagurung/db/";
	
	@Autowired
	private PostRepo postRepo;
	
	@Autowired
	private CategoryRepo catRepo;
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private Imagefile imageFile;
	
	@Autowired
	private ImageNameGenerator generator;

	@Override
	public String createPost(createPostDto postDto, long userId) {
		// TODO Auto-generated method stub
		String message = "Error! Post creation was unsuccessfull";
		
		Post post = new Post();
		BeanUtils.copyProperties(postDto, post);
		
		if (postDto.getCategoryId() != 0) {
		Category c =catRepo.findById(postDto.getCategoryId()).get();
		post.setCategory(c);
		}
		
		//add user details
		post.setUser(userRepo.findById(userId).get());
		
		Post savePost = postRepo.save(post);
		
		if (savePost != null) {
			//post is successfully created
			message = "Succesfully created new Post!";
		}
		return message;
	}

	@Override
	public List<PostDetailDto> viewAllPost() {
		// TODO Auto-generated method stub
		List<Post> posts = postRepo.findAll();
		List<PostDetailDto> postsDto = new ArrayList<>();
		for (Post p: posts) {
			PostDetailDto PostDetailDto = new PostDetailDto();
			BeanUtils.copyProperties(p, PostDetailDto);
			postsDto.add(PostDetailDto);
		}
		
		return postsDto;
	}


	@Override
	public String updatePost(createPostDto postDto, long postId, long userId) {
		// TODO Auto-generated method stub
		
		//check if the post exists
		Optional<Post> p = postRepo.findById(postId);
		
		if (p.isPresent()) {
			Post exist = p.get();
			if (exist.getUser().getUserId() == userId) {
				BeanUtils.copyProperties(postDto, exist);
				if (postDto.getCategoryId() != 0) {
					exist.setCategory(catRepo.findById(postDto.getCategoryId()).get());
				}
				exist.setUpdateDate(new Date ());
				
				Post update = postRepo.save(exist);
				if (update != null) {
				return "Successfully updated the post";
				}
			}else throw new ResourceNotFoundException("User is not Authorized to make changes!");
		} else throw new ResourceNotFoundException("Error! Invalid post id!");
		
		return "Update unsuccessful!";
	}

	@Override
	public String deletePost(long postId, long userId) {
		// TODO Auto-generated method stub
		//check if the post exists
		Optional<Post> p = postRepo.findById(postId);
		
		if (p.isPresent()) {
			Post exist = p.get();
			//Validate User Authorizaion
			if (exist.getUser().getUserId() == userId) {
				postRepo.delete(exist);
			return "Successfully deleted the post";
			
			}else throw new ResourceNotFoundException("User is not Authorized to make changes!");

		}else throw new ResourceNotFoundException("Error! Invalid post id!");
	}

	@Override
	public PostDetailDto viewPostDetail(long postId) {
		// TODO Auto-generated method stub
		Optional<Post> p = postRepo.findById(postId);
		
		if (p.isEmpty()) {
		throw new ResourceNotFoundException("Error! Invalid post id!");
		}
		else {
			Post post = p.get();
		PostDetailDto PostDetailDto = new PostDetailDto();
		BeanUtils.copyProperties(post, PostDetailDto);
		
		return PostDetailDto;
		}
	}

	@Override
	public List<PostDetailDto> viewPostByUser(long userId) {
		// TODO Auto-generated method stub
		User u = userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("Invalid User ID!"));
		List<Post> postByUser = u.getPosts();
		List<PostDetailDto> postsDto = new ArrayList<>();
		for (Post p: postByUser) {
			PostDetailDto postDetailDto = new PostDetailDto();
			BeanUtils.copyProperties(p, postDetailDto);
			postsDto.add(postDetailDto);
		}
		
		return postsDto;
		}

	@Override
	public String uploadImage(MultipartFile  file, long postId, long userId) {
		// TODO Auto-generated method stub
		Optional<Post> p = postRepo.findById(postId);
		
		if (p.isPresent()) {
			Post post = p.get();
			String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS"));
			String imageName = timestamp + generator.getFileExtensionName(file.getOriginalFilename());
			String imagePath = DB_PATH +imageName;
			
			//check if the uploaded file is an image
			if (imageFile.isImageFile(file)) {
				//create folder if it doesn't exist
				File folder = new File (DB_PATH);
				if (!folder.exists()) {
					folder.mkdirs();
				}
				try {
					file.transferTo(new File(imagePath));
				}catch (IOException e) {
					e.printStackTrace();
				}
				
				post.setPostImage(imagePath);
				post.setUpdateDate(new Date());
				Post save = postRepo.save(post);
				if (save != null) {
					return "Image Successfully saved";
				}
			}else throw new ResourceNotFoundException("ERROR Only Image file are allowed!");
		}else throw new ResourceNotFoundException("ERROR Invalid postID!");
		
		return "Failed to update image";
	}

	@Override
	public byte[] viewImage(long postId) {
		// TODO Auto-generated method stub
		
		Optional<Post> p = postRepo.findById(postId);
		if (p.isPresent()) {
			String filePath = p.get().getPostImage();
			
			if (filePath !=null) {
				try {
					return Files.readAllBytes(Paths.get(filePath));
				}catch (IOException e) {
					e.printStackTrace();
				}
			}else throw new ResourceNotFoundException ("Image Not Found in the post");
			
		}else throw new ResourceNotFoundException("ERROR Invalid postID!");
		
		return null;


	}

}
