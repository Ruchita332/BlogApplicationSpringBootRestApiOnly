package com.ruchi.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table (name = "POST")
public class Post {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column (name = "POST_ID")
	private long post_id;
	
	@Column (name = "TITLE")
	private String postTitle;
	
	@Column (name = "CONTENT")
	private String postContent;
	
	@Column (name ="IMAGE_FILE")
	private String postImage;
	
	@Column (name ="CREATED_DATE", updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdDate = new Date();
	
	@Temporal ( TemporalType.TIMESTAMP)
	@Column (name ="UPDATED_DATE", insertable = false)
	private Date updateDate = new Date();
	
	@ManyToOne
	@JoinColumn (name ="CATEGORY_ID")
	private Category category;
	
	@ManyToOne
	@JoinColumn (name ="USER_ID")
	private User user;
	
	@OneToMany (mappedBy="post", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Comment> comments = new ArrayList<>();
	
	
	
	
	

}
