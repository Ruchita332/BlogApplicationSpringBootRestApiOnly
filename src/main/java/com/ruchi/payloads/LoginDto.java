package com.ruchi.payloads;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginDto {
	
	@NotEmpty
	@Email (message ="Email format is not valid")
	private String email;
	
	@NotEmpty
	private String password;

}
