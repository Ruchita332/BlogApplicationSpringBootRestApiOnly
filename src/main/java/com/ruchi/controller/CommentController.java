package com.ruchi.controller;


import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ruchi.payloads.CommentDto;
import com.ruchi.service.CommentService;

import javax.validation.Valid;

@RestController
@RequestMapping ("/blog/user/{userId}/post/{postId}")
@CrossOrigin(origins ="*")
public class CommentController {
	
	@Autowired
	private CommentService commentService;
	
		
	//Handling COMMENT request
	@PostMapping("/comment/create")
	public ResponseEntity<String> createComment(@Valid @RequestBody CommentDto commentDto, @PathVariable  long userId, @PathVariable long postId) {
		String created = commentService.addcomment(commentDto, userId, postId);
		
	return ResponseEntity.created(null).body(created);
	}
	
	@PutMapping("/comment/{commentId}/update")
	public ResponseEntity<String> updateComment  (@RequestBody CommentDto commentDto, @PathVariable long userId, @PathVariable long commentId) {
		String updated = commentService.updateComment(commentDto, userId, commentId);
		
	return ResponseEntity.ok().body(updated);
	}
	@DeleteMapping("comment/{commentId}/delete")
	public ResponseEntity<String> deleteComment  (@PathVariable long userId, @PathVariable long commentId) {
		String deleted = commentService.deleteComment(userId, commentId);
		
	return ResponseEntity.ok().body(deleted);
	}
	
	

}
