package com.ruchi.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ruchi.entity.Comment;
import com.ruchi.entity.Role;
import com.ruchi.payloads.LoginDto;
import com.ruchi.payloads.RegisterDto;
import com.ruchi.payloads.ResetPasswordDto;
import com.ruchi.payloads.UpdateUserDetailsDto;
import com.ruchi.payloads.UserDetailsDto;
import com.ruchi.service.UserService;

import javax.validation.Valid;

@RestController
@RequestMapping ("/blog/user")
@CrossOrigin(origins ="*")
public class UserController {
	
	@Autowired
	private UserService userService;
	
		
	@PostMapping ("/register")
	public ResponseEntity<Map<String, String>> registerUser (@Valid @RequestBody RegisterDto userDto){
		String userRegistration = userService.userRegister(userDto);
		Map<String, String> response= new HashMap<>();
		response.put("message: ", userRegistration);
		
		return new ResponseEntity<>(response, HttpStatus.CREATED);
		
	}
	
	@PostMapping("/verify/{otp}")
	public ResponseEntity<Map<String, String>> verifyOTP (@PathVariable String otp) {
		String verifyOtp = userService.verifyOtp(otp);
		
		Map<String, String> response = new HashMap<>();
		response.put("message", verifyOtp);
			
		return ResponseEntity.ok().body(response);
	}
	
	@PostMapping("/login")
	public ResponseEntity<Map<String, String>> login (@Valid @RequestBody LoginDto userLogin) {
		String login = userService.userLogin(userLogin);
		
		Map<String, String> response = new HashMap<>();
		response.put("message", login);
			
		return ResponseEntity.ok().body(response);
	}
	
	@GetMapping("/{userId}")
	public ResponseEntity<UserDetailsDto> viewUserInfo (@PathVariable long userId) {
			UserDetailsDto viewUserDetails = userService.viewUserDetail(userId);
			
			
		return ResponseEntity.ok().body(viewUserDetails);
	}
	
	@GetMapping("/{userId}/trial")
	public ResponseEntity<Set<Role>> trial (@PathVariable long userId) {
			UserDetailsDto viewUserDetails = userService.viewUserDetail(userId);
			Set<Role> role = viewUserDetails.getRoles();
			
		return ResponseEntity.ok().body(role);
	}
	
	@PutMapping("/update")
	public ResponseEntity<String> updateUser (@Valid @RequestBody UpdateUserDetailsDto updateUserDto) {
			String updateUser = userService.updateUserDetails(updateUserDto);
			
		return ResponseEntity.ok().body(updateUser);
	}
	
	@PutMapping("/changepassword")
	public ResponseEntity<String> changePassword (@Valid @RequestBody ResetPasswordDto resetPasswordDto) {
			String resetPassword = userService.changePassword(resetPasswordDto);
			
		return ResponseEntity.ok().body(resetPassword);
	}
	
	@GetMapping("/forgotpassword/{email}")
	public ResponseEntity<String> forgorPasswrod (@PathVariable String email) {
			String forgotPassword = userService.forgotPassword(email);
			
		return ResponseEntity.ok().body(forgotPassword);
	}

	


	
	

}
