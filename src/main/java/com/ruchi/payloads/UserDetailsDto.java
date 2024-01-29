package com.ruchi.payloads;

import java.lang.annotation.Repeatable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.ruchi.entity.Comment;
import com.ruchi.entity.Post;
import com.ruchi.entity.Role;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDetailsDto {
	
	
	
	private long userId;
	
	private String userName;
		
	private String email;
	
	private String phone;
	
	private String password;
	
	@JsonIgnore
	private String otp;
	
	@JsonIncludeProperties("roleName")
	private Set<Role> roles = new HashSet<>();
	
		
	@JsonIncludeProperties("postTitle")

	List<Post> posts = new ArrayList<>();
	
	private Date creationDate = new Date();
	
	@JsonIgnore
	private Date updateDate = new Date();
	 
	@JsonIncludeProperties("commentContent")
	List<Comment> comments = new ArrayList<>();
	
	
	

	

}
