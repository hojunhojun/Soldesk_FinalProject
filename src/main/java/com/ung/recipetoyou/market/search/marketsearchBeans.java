package com.ung.recipetoyou.market.search;

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
@Entity(name = "rty_search_rank")
public class marketsearchBeans {
	
	@Id
	@SequenceGenerator(sequenceName = "rty_search_seq", name = "rsq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "rsq")
	@Column(name = "rtr_id")
	private Integer rtrid;
	
	@Column(name = "rtr_keyword")
	private String marketsearchingarea;
	
	@Column(name = "rtr_search_count")
	private Integer searchCount;
}
