package com.ung.recipetoyou.market;

import org.json.JSONObject;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Getter
public class marketresultbeans {
	private String title;
	private int price;
	private String productid;
	private String link;
	private String img;
	
	public marketresultbeans(JSONObject itemjson) {
		this.title = itemjson.getString("title");
		this.price = itemjson.getInt("lprice");
		this.productid = itemjson.getString("productId");
		this.link = itemjson.getString("link");
		this.img = itemjson.getString("image");
	}
}
