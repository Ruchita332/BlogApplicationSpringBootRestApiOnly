package com.ruchi.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table (name="COMMENT")
public class Comment {
	
	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	
	@Column(name = "COMMENT_ID")
	private long commentId;
	
	@Column(name = "Comment")
	private String commentContent;
	
	@ManyToOne
	@JoinColumn (name ="USER_ID")
	private User user;
	
	@ManyToOne
	@JoinColumn (name ="POST_ID")
	private Post post;

}
