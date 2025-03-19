package com.ung.recipetoyou.market;

import java.util.Date;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedDate;

import com.ung.recipetoyou.market.search.marketresultBeans;

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
@Entity(name = "rty_Market_Keyword_Price")
public class searchDTApriceBeans{
	
	@Id
	@SequenceGenerator(sequenceName = "rMKP_seq", name = "rmkpseq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "rmkpseq")
	@Column(name = "rMKP_id")
	private Integer rmkpid;
	
	@Column(name = "rMKP_schkey")
	private String rmkpschy;
	
	@Column(name = "rMKP_aver")
	private Integer rmkpav;
	
	@Column(name = "rMKP_low")
	private Integer rmkplw;

	@Column(name = "rMKP_high")
	private Integer rmkphg;
	
	@Column(name = "rMKP_pyun")
	private Integer rmkppn;
	
	@Column(name = "rMKP_DTACT")
	private Integer rmkpDCTN;
	
	@Column(name = "rMKP_title")
	private String rmkptt;
	
	@CreatedDate
	@Column(name = "rMKP_DATE")
	private Date rmkpdt;
}
