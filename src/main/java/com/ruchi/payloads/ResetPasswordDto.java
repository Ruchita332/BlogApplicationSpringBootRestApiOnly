package com.ruchi.payloads;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ResetPasswordDto {
	
	@NotEmpty
	@Email (message ="Email format is not correct!")
	private String email;
	
	@NotEmpty (message ="New Password cannot be blank!")
	private String newPassword;
	
	@NotEmpty (message ="Confirm Password cannot be blank!")
	private String confirmPassword;

}
