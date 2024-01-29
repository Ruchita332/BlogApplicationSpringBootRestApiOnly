package com.ruchi.payloads;


import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class createPostDto {
	
	
	@NotBlank
	@JsonProperty("title")
	private String postTitle;
	
	@NotBlank
	@JsonProperty("content")
	private String postContent;
	
	@JsonProperty("cid")
	private long categoryId;


		
}
