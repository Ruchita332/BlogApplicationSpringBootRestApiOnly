package com.ruchi.payloads;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.ruchi.entity.Post;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDetailDto {
	
	private long categoryId;
	
	@JsonProperty("name")
	private String categoryName;
	
	@JsonIncludeProperties("postTitle")
	private List<Post> posts = new ArrayList<>();

}
