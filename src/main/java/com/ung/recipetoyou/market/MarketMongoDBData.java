package com.ung.recipetoyou.market;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Document(collection = "rty_marketdbtable_mongus")
public class MarketMongoDBData {
	
	@Id
	@Field("rtym_prodid")
	private String rtymid;
	
	@Field("rtym_title")
	private String rtymtt;
	
	@Field("rtym_link")
	private String rtymlnk;
	
	@Field("rtym_img")
	private String rtymimg;
	
	@Field("rtym_price")
	private Integer rtymprce;
	
	@Field("rtym_mallname")
	private String rtymmn;
	
	@Field("rtym_brand")
	private String rtymbd;
	
	@Field("rtym_maker")
	private String rtymmk;
	
	@Field("rtym_cate1")
	private String rtymct1;
	
	@Field("rtym_cate2")
	private String rtymct2;
	
	@Field("rtym_cate3")
	private String rtymct3;
	
	@Field("rtym_cate4")
	private String rtymct4;
	
	@Field("rtym_querykey")
	private String rtymqk;
	
	public void updateFrom(MarketMongoDBData data) {
	    this.setRtymtt(data.getRtymtt());
	    this.setRtymlnk(data.getRtymlnk());
	    this.setRtymimg(data.getRtymimg());
	    this.setRtymprce(data.getRtymprce());
	    this.setRtymmn(data.getRtymmn());
	    this.setRtymbd(data.getRtymbd());
	    this.setRtymmk(data.getRtymmk());
	    this.setRtymct1(data.getRtymct1());
	    this.setRtymct2(data.getRtymct2());
	    this.setRtymct3(data.getRtymct3());
	    this.setRtymct4(data.getRtymct4());
	    this.setRtymqk(data.getRtymqk());
	}
}
