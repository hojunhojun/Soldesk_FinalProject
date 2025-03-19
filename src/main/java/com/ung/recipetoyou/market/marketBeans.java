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
public class marketBeans {
	private String title;
	private String link;
	private String img;
	private int price;
	private String productid;
	private String mallname;
	private String brand;
	private String maker;
	private String cate1;
	private String cate2;
	private String cate3;
	private String cate4;

	public marketBeans(JSONObject itemjson) {
		this.title = itemjson.getString("title");
		this.link = itemjson.getString("link");
		this.img = itemjson.getString("image");
		this.price = itemjson.getInt("lprice");
		this.mallname = itemjson.getString("mallName");
		this.brand = itemjson.getString("brand");
		this.maker = itemjson.getString("maker");
		this.cate1 = itemjson.getString("category1");
		this.cate2 = itemjson.getString("category2");
		this.cate3 = itemjson.getString("category3");
		this.cate4 = itemjson.getString("category4");
		this.productid = itemjson.getString("productId");
	}
}
