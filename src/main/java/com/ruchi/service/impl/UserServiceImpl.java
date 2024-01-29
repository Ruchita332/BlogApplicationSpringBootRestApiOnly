package com.ruchi.service.impl;


import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ruchi.entity.Post;
import com.ruchi.entity.Role;
import com.ruchi.entity.User;
import com.ruchi.exceptions.InvalidEmailException;
import com.ruchi.exceptions.PasswordMisMatchException;
import com.ruchi.exceptions.ResourceNotFoundException;
import com.ruchi.exceptions.DuplicateItemException;
import com.ruchi.payloads.LoginDto;
import com.ruchi.payloads.RegisterDto;
import com.ruchi.payloads.ResetPasswordDto;
import com.ruchi.payloads.UpdateUserDetailsDto;
import com.ruchi.payloads.UserDetailsDto;
import com.ruchi.repo.RoleRepo;
import com.ruchi.repo.UserRepo;
import com.ruchi.service.UserService;
import com.ruchi.utils.EmailSender;
import com.ruchi.utils.PasswordGenerator;
import com.ruchi.utils.SmsSender;

import net.bytebuddy.implementation.bytecode.Throw;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepo userRepo;
	@Autowired
	private RoleRepo roleRepo;
//	@Autowired
//	private SmsSender smsSender;
	@Autowired
	private EmailSender emailSender;
	
	@Override
	public String userRegister(RegisterDto userDto) {
		// TODO Auto-generated method stub
		
		String message ="User Already Exist!";

		Optional<User> u = userRepo.findByEmail(userDto.getEmail());
		if (u.isPresent()) {
			throw new ResourceNotFoundException("ERROR! User with " + userDto.getEmail() + " email alreay exists!");
		}
		
		else{ //User is a new user
			User user = new User();
			
			BeanUtils.copyProperties(userDto, user);
			
			//Generate and send otp
			String otp = emailSender.sendOtp(userDto.getEmail());
			
			message = "Failed to send OTP";
			
			if (otp!= null) { //otp sent
				user.setOtp(otp);
				User saveUser = userRepo.save(user);
				if (saveUser != null) {
					message = "Please Verify, OTP sent to EMail.";
				}
//				//send otp to phone
//				if (user.getPhone() != null) {
//				try {
//					message += smsSender.sendSms(user.getPhone(), otp);
//				}catch (Exception e) {
//					e.toString();
//					message += "Otp couldn't be sent to Cellphone";
//				}
				
			}
		}
				
		return message;
	}

	@Override
	public String verifyOtp(String otp) {
		// TODO Auto-generated method stub
		
		Optional<User> u = userRepo.findByOtp(otp);
		
		String message ="Registration Failed";
		
		if (u.isEmpty()) {
			//user with given otp doens't exist
			throw new ResourceNotFoundException("ERROR! Invalid Otp! User doesn't exist");
		}
		else if (otp.equals(u.get().getOtp())) {
			//set a random password
			User user = u.get();
			String randomPassword = PasswordGenerator.getRandomPassword();
			user.setPassword(randomPassword);
			user.setOtp(""); //empty string
			Role role = roleRepo.findByRoleName("USER").orElseThrow(
					() -> new ResourceNotFoundException("Default roles initializer not working, roles not found"));			user.setCreationDate(new Date());
			user.getRoles().add(role);
			role.getRolesUsers().add(user);
					//save updated info
			User updatedUser = userRepo.save(user);
			
			if (updatedUser != null) {
				//user successfully registered
				emailSender.sendMail(updatedUser.getEmail(), randomPassword);
				message ="Registration Successfull";
			}
		}
				
		return message;
	}

	@Override
	public String forgotPassword(String email) {
		// TODO Auto-generated method stub
		
		Optional<User> u = userRepo.findByEmail(email);
		
		
		if (u.isPresent()) {
			//user found
			String passcode = emailSender.sendMail(email, u.get().getPassword());
			if (passcode != null) {
				return "Password sent to email";
				}
			else return "Error sending password. Please try again!";
		}
		else {
			throw new ResourceNotFoundException("Error! Invalid  Email "+ email + "! User doesn't exist!" );
		}
		
	}

	@Override
	public UserDetailsDto viewUserDetail(long userId) {
		// TODO Auto-generated method stub
		
		Optional<User> u = userRepo.findById(userId);
		
		if (u.isPresent()) {
			//user found 
			UserDetailsDto userDto = new UserDetailsDto();
			BeanUtils.copyProperties(u.get(), userDto);
			
			return userDto;
		}
		else throw new ResourceNotFoundException("Error! Invalid userId! User doesn't exist!" );
	}

	@Override
	public String changePassword(ResetPasswordDto resetPasswordDto) {
		// TODO Auto-generated method stub
		
		//Get user info
		Optional<User> u = userRepo.findByEmail(resetPasswordDto.getEmail());
		String message = "password reset unsuccessfull";
		
		if (u.isPresent()) {
			//check if new password and confirmed new password value are same
			if (resetPasswordDto.getNewPassword().equals(resetPasswordDto.getConfirmPassword())){
				//change the password
				User user= u.get();
				user.setPassword(resetPasswordDto.getNewPassword());
				user.setUpdateDate(new Date());

				User save = userRepo.save(user);
				
				if (save != null) {
					//Save successfull
					message ="Password reset successfull";
				}
			} else {
				throw new PasswordMisMatchException("Error! New Password and Confirm Password details doesn't match!");
			}
		}else {
			throw new ResourceNotFoundException("Error! Invalid Email "+ resetPasswordDto.getEmail() + "! User doesn't exist!" );
		}
		
		return message;
	}

	@Override
	public String userLogin(LoginDto user) {
		// TODO Auto-generated method stub
		Optional<User> u =  userRepo.findByEmail(user.getEmail());
		
		if (u.isPresent()) {
				if (u.get().getPassword().equals(user.getPassword())) {
				return "Login Successful. Welcome  "+ u.get().getUserName();
			}else {
				throw new ResourceNotFoundException("Error! Invalid Password " );
			}

		}else throw new ResourceNotFoundException("Error! Invalid Email "+ user.getEmail() + "! User doesn't exist!" );
		
		
	}
	
	@Override
	public String updateUserDetails (UpdateUserDetailsDto updateUserDetails) {
		
		String message = "Update unsccessfull!";
		
		Optional<User> u = userRepo.findByEmail(updateUserDetails.getEmail());
		
		if (u.isPresent()) {
			//user found 
			//update details
			User user = u.get();
			BeanUtils.copyProperties(updateUserDetails, user);
			user.setUpdateDate(new Date());
			User save = userRepo.save(user);
			
			if (save != null) {
				message = "User "+ user.getUserName()+" successfully saved!";
			}
			else {
				message ="ERROR! Update unsuccessfull!";
			}
		}else  throw new ResourceNotFoundException("Error! Invalid Email "+ updateUserDetails.getEmail() + "! User doesn't exist!" );

		
		return message;
	}

}
