package com.ruchi.controller;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ruchi.payloads.CategoryDetailDto;
import com.ruchi.payloads.CategoryDto;
import com.ruchi.payloads.PostDetailDto;
import com.ruchi.payloads.RoleDto;
import com.ruchi.service.CategoryService;
import com.ruchi.service.RoleService;

import javax.validation.Valid;

@RestController
@RequestMapping ("/blog/user/{userId}")
@CrossOrigin(origins ="*")
public class RoleController {
	
	@Autowired
	private RoleService roleService;
	
	//Handling Role request
	@GetMapping("/roles")
	public ResponseEntity<Set<RoleDto>> viewUserRole(@PathVariable  long userId) {
		Set<RoleDto> userRoles= roleService.viewUserRole(userId);
		
	return ResponseEntity.created(null).body(userRoles);
	}
	@PostMapping("/role/create")
	public ResponseEntity<String> createRole(@Valid @RequestBody RoleDto roleDto, @PathVariable  long userId) {
		String created = roleService.createRole(roleDto, userId);
		
	return ResponseEntity.created(null).body(created);
	}
	
	@PutMapping("/role/{roleId}/update")
//	http://localhost:3000/blog/user/1/category/1/update
	public ResponseEntity<String> updateRole  (@RequestBody RoleDto roleDto, @PathVariable long userId, @PathVariable long roleId) {
		String updated = roleService.updateRole(roleDto, roleId, userId);
		
	return ResponseEntity.ok().body(updated);
	}
	
	@DeleteMapping("role/{roleId}/delete")
	public ResponseEntity<String> deleteRole  (@PathVariable long userId, @PathVariable long roleId) {
		String deleted = roleService.deleteRole(roleId, userId);
		
	return ResponseEntity.ok().body(deleted);
	}
	
	
}
