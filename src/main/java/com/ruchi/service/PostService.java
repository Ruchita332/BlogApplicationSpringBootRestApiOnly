package com.ruchi.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.ruchi.payloads.PostDetailDto;
import com.ruchi.payloads.createPostDto;

public interface PostService {
	public String createPost(createPostDto postDto, long userId);
	public PostDetailDto viewPostDetail (long postId);
	public List<PostDetailDto> viewAllPost();
	public String updatePost(createPostDto postDto, long postId, long userId);
	public String deletePost (long postId, long userId);
	public List<PostDetailDto> viewPostByUser (long userId);
	public String uploadImage (MultipartFile file, long postId, long userId);
	public byte[] viewImage(long postId);
	
}
