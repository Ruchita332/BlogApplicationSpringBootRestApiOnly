package com.ruchi.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.ruchi.entity.Category;
import com.ruchi.entity.Comment;
import com.ruchi.entity.Post;
import com.ruchi.entity.Role;
import com.ruchi.entity.User;
import com.ruchi.payloads.ReportDto;
import com.ruchi.repo.CategoryRepo;
import com.ruchi.repo.CommentRepo;
import com.ruchi.repo.PostRepo;
import com.ruchi.repo.RoleRepo;
import com.ruchi.repo.UserRepo;
import com.ruchi.service.ReportService;


@RestController
@RequestMapping ("/blog/report")
public class ReportsController {
	
	@Autowired
	private ReportService reportService;
	@Autowired
	private RoleRepo roleRepo;
	@Autowired
	private UserRepo userRepo;
	@Autowired
	private PostRepo postRepo;
	@Autowired
	private CommentRepo commentRepo;
	@Autowired
	private CategoryRepo categoryRepo;
	
	@Autowired
	private ReportDto data;
	
	public void getData() {
		List<User> users = userRepo.findAll();
		List<Post> posts = postRepo.findAll();
		List<Comment> comments = commentRepo.findAll();
		List<Role> roles = roleRepo.findAll();
		List<Category> categories = categoryRepo.findAll();
		data.setRoles(roles);
		data.setUsers(users);
		data.setPosts(posts);
		data.setComments(comments);
		data.setCategories(categories);
	}

	@GetMapping("/pdf")
	public ResponseEntity<?> generatePdfReport() {
		getData();
		try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
			reportService.generatePdfReport(data, outputStream);

			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_PDF);
			headers.setContentDispositionFormData("attachment", "report.pdf");

			return new ResponseEntity<>(outputStream.toByteArray(), headers, HttpStatus.OK);

		} catch (IOException e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error generating PDf file");
		}
	}

	@GetMapping("/excel")
	public ResponseEntity<?> generateExcelReport() {
		getData();
		try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
			reportService.generateExcelReport(data, outputStream);
			
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_OCTET_STREAM );
			headers.setContentDispositionFormData("attachment", "excel.xlsx");
			return new ResponseEntity<>(outputStream.toByteArray(), headers, HttpStatus.OK);
		

		} catch (IOException e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error generating PDf file");
		}
	}
}
