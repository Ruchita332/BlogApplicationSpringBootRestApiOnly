package com.ruchi.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import javax.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table (name = "USER_DETAILS")
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name ="USER_ID")
	@JsonIgnore
	private long userId;
	
	@Column (name ="USER_NAME")
	private String userName;
		
	@Column (name = "EMAIL")
	private String email;
	
	@Column (name = "PHONE")
	private String phone;
	
	@Column (name = "PASSWORD")
	private String password;
	
	@JsonIgnore
	@Column (name ="OTP")
	private String otp;
	
//	@JsonIgnore
//	@Column (name ="ROLE")
//	private long roleId;
	
	@ManyToMany(mappedBy = "rolesUsers")
	private Set<Role> roles = new HashSet<>();
	
		
	@OneToMany (mappedBy ="user", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	List<Post> posts = new ArrayList<>();
	
	@OneToMany (mappedBy = "user",fetch = FetchType.LAZY, cascade =CascadeType.ALL, orphanRemoval = true)
	List<Comment> comments = new ArrayList<>();
	
	
	@Column(updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date creationDate = new Date();

	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(insertable = false)
	private Date updateDate = new Date();
	
	

	

}
