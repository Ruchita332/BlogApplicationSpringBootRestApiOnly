package com.ruchi.service;

import com.ruchi.payloads.CommentDto;

public interface CommentService {
	public String addcomment(CommentDto commentDto,long userId, long postId);
	public String updateComment(CommentDto commentDto, long userId, long commentId);
	public String deleteComment (long userId, long commentId);
}
