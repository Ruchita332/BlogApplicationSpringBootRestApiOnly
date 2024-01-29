package com.ruchi.service;

import com.ruchi.payloads.LoginDto;
import com.ruchi.payloads.RegisterDto;
import com.ruchi.payloads.ResetPasswordDto;
import com.ruchi.payloads.UpdateUserDetailsDto;
import com.ruchi.payloads.UserDetailsDto;

public interface UserService {
	
	public String userRegister (RegisterDto userDto);
	
	public String verifyOtp (String otp);
	
	public String userLogin(LoginDto user);
	
	public String forgotPassword (String email);
	
	public String changePassword (ResetPasswordDto resetPasswordDto);
	
	public UserDetailsDto viewUserDetail (long userId);

	public String updateUserDetails(UpdateUserDetailsDto updateUserDetails);
	

}
