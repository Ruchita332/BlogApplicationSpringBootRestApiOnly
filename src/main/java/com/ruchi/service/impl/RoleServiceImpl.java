package com.ruchi.service.impl;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ruchi.payloads.RoleDto;
import com.ruchi.entity.Role;
import com.ruchi.entity.User;
import com.ruchi.exceptions.DuplicateItemException;
import com.ruchi.exceptions.ResourceNotFoundException;
import com.ruchi.repo.RoleRepo;
import com.ruchi.repo.UserRepo;
import com.ruchi.service.RoleService;
import com.ruchi.utils.EmailSender;

@Service
public class RoleServiceImpl implements RoleService {
	@Autowired
	private RoleRepo roleRepo;

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private EmailSender emailSender;

	@PostConstruct
	public void init() {
	    initializeDefaultRoles();
	    initializeAdminUser();
	}

	private void initializeDefaultRoles() {
	    createRoleIfNotExists("ADMIN");
	    createRoleIfNotExists("USER");
	}

	private void initializeAdminUser() {
	    if (!userRepo.findByEmail("ruchitagrng@gmail.com").isPresent()) {
	        User adminUser = createNewAdminUser("ADMIN", "ruchitagrng@gmail.com");

	        try {
	            // Send OTP via email
	            emailSender.sendOtp(adminUser.getEmail());
	        } catch (Exception e) {
	            // Handle email sending failure (log or provide a user-friendly message)
	            System.out.println("Failed to send OTP. Please try again.");
	        }
	    }
	}

	private User createNewAdminUser(String name, String email) {

	    User adminUser = new User();
	    adminUser.setUserName(name);
	    adminUser.setEmail(email);

	    Role adminRole = roleRepo.findByRoleName("ADMIN").orElseThrow(
	            () -> new ResourceNotFoundException("Default roles initializer not working, roles not found"));

	    adminRole.getRolesUsers().add(adminUser);
	    adminUser.getRoles().add(adminRole);
	    adminUser.setPassword("admin");

	    // Save the role
	    roleRepo.save(adminRole);
//	    userRepo.save(adminUser);

	    return adminUser;
	}

	private void createRoleIfNotExists(String roleName) {
	    Optional<Role> existingRole = roleRepo.findByRoleName(roleName);
	    if (existingRole.isEmpty()) {
	        Role newRole = new Role();
	        newRole.setRoleName(roleName);
	        roleRepo.save(newRole);
	    }
	}

	@Override
	public String createRole(RoleDto roleDto, long userId) {
		
		String message = "Failed to save role";

		//Verify User is admin user
		User u = userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("Invalid User Id, User is not authorized to make changes"));
		Role admin = roleRepo.findByRoleName("ADMIN").get();
		
		if (u.getRoles().contains(admin)) { //user is authorized to make changes
		
			//create new role
			Optional<Role> exists =roleRepo.findByRoleName(roleDto.getRoleName());
			if (exists.isPresent()) {
				new DuplicateItemException("The role " + roleDto.getRoleName() + " already exists");
			}
			else {
				Role role = new Role();
				BeanUtils.copyProperties(roleDto, role);
		
				Role saveRole = roleRepo.save(role);
				if (saveRole != null) {
					message = "Roled saved successfully";
				}
			}
		}
		return message;
	}

	@Override
	public RoleDto getRole(long roleId) {
		Role existingRole = roleRepo.findById(roleId)
				.orElseThrow(() -> new ResourceNotFoundException("Role with role id " + roleId + " not found"));
		RoleDto roleDto = new RoleDto();
		BeanUtils.copyProperties(existingRole, roleDto);
		return roleDto;
	}
	
	@Override
	public Set<RoleDto> viewUserRole(long userId) {
		User user = userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User with User id " + userId + " not found"));
		Set<RoleDto> userRolesDto = new HashSet<>();
		Set<Role> userRoles = user.getRoles();
		
		for (Role r: userRoles) {
			RoleDto rdto = new RoleDto();
		
		BeanUtils.copyProperties(r, rdto);
		userRolesDto.add(rdto);
		}
		return userRolesDto;
	}


	@Override
	public String updateRole(RoleDto roleDto, long roldId, long userId) {
		String message = "Failed to update role";
		
		//Verify User is admin user
		User u = userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("Invalid User Id, User is not authorized to make changes"));
		Role admin = roleRepo.findByRoleName("ADMIN").get();
		
		if (u.getRoles().contains(admin)) {
			Role existingRole = roleRepo.findById(roldId)
					.orElseThrow(() -> new ResourceNotFoundException("Role with role id " + roldId + " not found"));
			BeanUtils.copyProperties(roleDto, existingRole);
	
			Role saveRole = roleRepo.save(existingRole);
			if (saveRole != null) {
				message = "Roled updated successfully";
			}
		}
		return message;
	}

	@Override
	public String deleteRole(long roleId, long userId) {
		String message = "Deletion Unsuccessful";
		
		//Verify User is admin user
		User u = userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("Invalid User Id, User is not authorized to make changes"));
		Role admin = roleRepo.findByRoleName("ADMIN").get();
		
		if (u.getRoles().contains(admin)) {
			Role existingRole = roleRepo.findById(roleId)
					.orElseThrow(() -> new ResourceNotFoundException("Role with role id " + roleId + " not found"));
			roleRepo.delete(existingRole);
			message = "Deletion Successful";
		}
			return message;
	}



}
