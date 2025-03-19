package com.ung.recipetoyou.recipe;

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
@Entity(name="rty_today_recipe")
public class TodayRecipe {
	@Id
	@SequenceGenerator(sequenceName = "rty_today_seq", name="rts", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="rts")
	@Column(name="rtr_no")
	private Integer no;
	
	@Column(name="rtr_name")
	private String name;
	
	@Column(name="rtr_rcpno")
	private Integer rno;
	
}
