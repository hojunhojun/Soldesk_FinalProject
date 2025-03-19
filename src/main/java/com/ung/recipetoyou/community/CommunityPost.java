package com.ung.recipetoyou.community;

import java.util.Date;

import org.hibernate.annotations.CreationTimestamp;

import com.ung.recipetoyou.member.Member;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity(name="rty_community")
public class CommunityPost {

	@Id
	@SequenceGenerator(sequenceName = "rty_community_seq", name="rcs", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="rcs")
	@Column(name="com_no")
	private Integer no;
	
	@ManyToOne
	@JoinColumn(name="com_writer")
	private Member writer;
	
	@Column(name="com_title")
	private String title;
	
	@Column(name="com_text")
	private String text;
	
	@CreationTimestamp
	@Column(name="com_date")
	private Date date;
	
	@Column(name="com_like")
	private Integer like;
	
	@Column(name="com_img1")
	private String img1;
	
	@Column(name="com_img2")
	private String img2;
	
	@Column(name="com_img3")
	private String img3;
	
	@Column(name="com_img4")
	private String img4;
	
	@Column(name="com_img5")
	private String img5;
	
	@Column(name="com_type")
	private Integer type;
	
	@Column(name="com_rcpno")
	private Integer rno;
	
	@Column(name="com_cate")
	private String cate;
}
