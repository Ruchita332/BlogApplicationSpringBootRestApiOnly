package com.ruchi.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping ("/blog")
@CrossOrigin(origins ="*")
public class BlogContoller {
	
	
	
	@GetMapping ("/home")
	public ResponseEntity<Map<String, String>> homePage (){
		Map<String, String> response= new HashMap<>();
		response.put("message: ", "Welome to home page");
		
		return new ResponseEntity<>(response, HttpStatus.CONTINUE);
		
	}
	
	

}
