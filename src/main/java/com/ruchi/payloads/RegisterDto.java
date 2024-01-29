package com.ruchi.payloads;



import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisterDto {
		
	@NotEmpty
	@Size (min = 2, max = 25, message ="Name must be between 2 to 25 character long")
	@Pattern(regexp = "^[a-zA-Z]*$", message = " name must not contain numbers or special characters")
	@JsonProperty("name")
	private String userName;
	
	@NotBlank
	@Email (message ="Email format is not correct!")
	private String email;
	
	
	@Pattern (regexp ="^\\d{10}$", message ="Phone number must contain 10 digits")
	private String phone;
	
}
