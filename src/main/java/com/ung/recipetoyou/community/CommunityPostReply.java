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
@Entity(name="rty_reply")
public class CommunityPostReply {
	
	@Id
	@SequenceGenerator(sequenceName = "rty_reply_seq", name="rrs", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="rrs")
	@Column(name="rr_no")
	private Integer no;
	
	@Column(name="rr_writer")
	private String writer;

	@Column(name="rr_text")
	private String text;
	
	@CreationTimestamp
	@Column(name="rr_date")
	private Date date;
	
	@Column(name="rr_par_no")
	private Integer parNo;
}
