package com.ung.recipetoyou.community;

import java.util.Date;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity(name="rty_comment")
public class CommunityPostComment {
	
	@Id
	@SequenceGenerator(sequenceName = "rty_comment_seq", name="rcs", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="rcs")
	@Column(name="rc_no")
	private Integer no;
	
	@Column(name="rc_writer")
	private String writer;

	@Column(name="rc_text")
	private String text;

	@CreationTimestamp
	@Column(name="rc_date")
	private Date date;
	
	@Column(name="rc_com_no")
	private Integer postNo;
	
	@Column(name="rc_com_type")
	private Integer postType;
}
	
