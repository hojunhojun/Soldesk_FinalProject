package com.ung.recipetoyou.arcade;

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
@Entity(name = "rty_arcade")
public class Quiz {
	@Id
	@SequenceGenerator(sequenceName = "rty_arcade_seq", name = "ras", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ras")
	@Column(name = "arc_pk")
	private Integer no;
	
	@Column(name = "arc_cor")
	private String cor;
	
	@Column(name = "arc_quiz")
	private String quiz;
	
	@Column(name = "arc_type")
	private String type;
}
