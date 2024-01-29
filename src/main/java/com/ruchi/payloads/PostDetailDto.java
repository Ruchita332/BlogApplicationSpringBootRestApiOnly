package com.ruchi.payloads;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import com.ruchi.entity.Category;
import com.ruchi.entity.Comment;
import com.ruchi.entity.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostDetailDto {
	
	
	private long post_id;
	
	private String postTitle;
	
	private String postContent;
	
	private String postImage;
	
	
	private Date createdDate = new Date();
	
	@JsonIgnore
	private Date updateDate = new Date();
	
	@JsonIncludeProperties("categoryName")
	private Category category;
	
	@JsonIncludeProperties ("userName")
	private User user;
	
	@JsonIncludeProperties("commentContent")
	List<Comment> comments = new ArrayList<>();
	
	
	
	

}
