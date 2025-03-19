package com.ung.recipetoyou.market.search;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity(name = "rty_product_AnalDTA")
public class marketresultBeans {
	
	@Id
	@Column(name = "rpA_prodid")
	private String rpaid;
	
	@Column(name = "rpA_title")
	private String rpatt;
	
	@Column(name = "rpA_price")
	private Integer rpapr;
	
	@Column(name = "rpA_img")
	private String rpaimg;
	
	@Column(name = "rpA_link")
	private String rpalnk;
	
	@Column(name = "rpA_schkey")
	private String rpaschk;
}
