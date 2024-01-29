package com.ruchi.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ruchi.entity.Comment;
import com.ruchi.entity.Post;
import com.ruchi.entity.User;
import com.ruchi.exceptions.ResourceNotFoundException;
import com.ruchi.payloads.CommentDto;
import com.ruchi.repo.CommentRepo;
import com.ruchi.repo.PostRepo;
import com.ruchi.repo.UserRepo;
import com.ruchi.service.CommentService;

@Service
public class CommentServiceImpl implements CommentService {

	@Autowired
	private CommentRepo commentRepo;
	@Autowired
	private UserRepo userRepo;
	@Autowired
	private PostRepo postRepo;
	
	@Override
	public String addcomment(CommentDto commentDto, long userId, long postId) {
		// TODO Auto-generated method stub
		User user = userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("UserId not valid!"));
		Post post = postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("PostId not valid!"));
		
		if (user != null && post != null) {
			Comment comment = new Comment();
			BeanUtils.copyProperties(commentDto, comment);
			comment.setUser(user);
			comment.setPost(post);
			post.getComments().add(comment);
			user.getComments().add(comment);
			Comment saveComment = commentRepo.save(comment);
						
			if (saveComment != null) {
				//Successfully saved
				return "Successfully Saved comment";
			}
			else return "Error Comment Couldn't be saved";
		}
		
		return "TO Do Invalid userId or postId";
	}

	@Override
	public String updateComment(CommentDto commentDto, long userId, long commentId) {
		// TODO Auto-generated method stub
		//check if the comment exists
		Comment exist = commentRepo.findById(commentId).get();
		
		if (exist != null) {
			//comment exists
			if (exist.getUser().getUserId() == userId) {
				//user is authorized to make changes
				BeanUtils.copyProperties(commentDto, exist);
				Comment save = commentRepo.save(exist);
				if (save != null) {
					return "Successfully updated comment";
				}
				else return "Error! Update unsuccessful!";
			}
			else return "Error! User is not authorized";
		}
		return "TO DO Comment doesn't exist";
	}

	@Override
	public String deleteComment(long userId, long commentId) {
		// TODO Auto-generated method stub
		
		//check if the comment exists
		Comment exist = commentRepo.findById(commentId).get();
		
		if (exist != null) {
			//comment exists 
			if (exist.getUser().getUserId() == userId) {
				//user is authorized to make changes
				//delete
				commentRepo.deleteById(commentId);
				return "Comment Successfully Deleted";
			}
			else return "Error! User is not authorized";
		}
		return "TO DO Comment doesn't exist";
	}

}
