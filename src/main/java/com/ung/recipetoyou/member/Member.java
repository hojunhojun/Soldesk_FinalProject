package com.ung.recipetoyou.member;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity(name = "rty_member")
public class Member {

	@Id
	@Column(name = "mem_id")
	private String id;

	@Column(name = "mem_pw")
	private String pw;

	@Column(name = "mem_name")
	private String name;

	@Column(name = "mem_nick")
	private String nick;

	@Column(name = "mem_pphoto")
	private String pphoto;

	@Column(name = "mem_bir")
	private Date bir;

	@Column(name = "mem_addr")
	private String addr;

	@Column(name = "mem_bg")
	private String bg;

	@Column(name = "mem_type")
	private String type;
}