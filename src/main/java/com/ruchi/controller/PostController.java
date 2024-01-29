package com.ruchi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ruchi.payloads.PostDetailDto;
import com.ruchi.payloads.createPostDto;
import com.ruchi.service.PostService;

import javax.validation.Valid;

@RestController
@RequestMapping ("/blog/user/{userId}")
@CrossOrigin(origins ="*")
public class PostController {
	
	@Autowired
	private PostService postService;
	
	
	//Handling POST request
	@GetMapping("/posts")
	public ResponseEntity<List<PostDetailDto>> viewAllPost () {
		List<PostDetailDto> posts = postService.viewAllPost();
		
	return ResponseEntity.ok().body(posts);
	}
	
	@GetMapping("/post/{postId}")
	public ResponseEntity<PostDetailDto> viewPostDetail (@PathVariable long postId) {
		PostDetailDto postDto = postService.viewPostDetail(postId);
		
	return ResponseEntity.ok().body(postDto);
	}
	
	@PostMapping("/post/create")
	public ResponseEntity<String> createPost (@Valid @RequestBody createPostDto postDto, @PathVariable long userId) {
		String created = postService.createPost(postDto, userId);
		
	return ResponseEntity.created(null).body(created);
	}
	
	@PostMapping("/post/{postId}/uploadImage")
	public ResponseEntity<String> uploadImageInPost (@RequestParam("image") MultipartFile file, @PathVariable long userId, @PathVariable long postId) {
		String uploaded = postService.uploadImage(file, postId, userId);
		
	return ResponseEntity.ok().body(uploaded);
	}
	
	@GetMapping("/post/{postId}/image")
	public ResponseEntity<byte[]> viewImageInPost (@PathVariable long postId) {
		byte[] viewImage = postService.viewImage(postId);
		
		return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.valueOf("image/png")).body(viewImage);
		//	return ResponseEntity.ok().body(viewImage);
	}
	
	@GetMapping("/posts/byUser")
	public ResponseEntity<List<PostDetailDto>> viewPostsByUser (@PathVariable long userId) {
		List<PostDetailDto> posts = postService.viewPostByUser(userId);
		
	return ResponseEntity.ok().body(posts);
	}
	
	@PutMapping("post/{postId}/update")
	public ResponseEntity<String> updatePost (@Valid @RequestBody createPostDto postDto, @PathVariable long postId, @PathVariable long userId) {
		String updated = postService.updatePost(postDto, postId, userId);
		
	return ResponseEntity.ok().body(updated);
	}
	
	@DeleteMapping("post/{postId}/delete")
	public ResponseEntity<String> deletePost (@PathVariable long postId, @PathVariable long userId) {
		String deleted = postService.deletePost(postId, userId);
		
	return ResponseEntity.ok().body(deleted);
	}
	
	

}
