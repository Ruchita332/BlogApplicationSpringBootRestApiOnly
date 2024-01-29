package com.ruchi.payloads;

import java.util.List;

import org.springframework.stereotype.Component;

import com.ruchi.entity.Category;
import com.ruchi.entity.Comment;
import com.ruchi.entity.Post;
import com.ruchi.entity.Role;
import com.ruchi.entity.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class ReportDto {
	private List<Role> roles;
	private List<User> users;
	private List<Category> categories;
	private List<Post> posts;
	private List<Comment> comments;
}
