package com.ung.recipetoyou.recipe;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Document(collection = "recipe_main_db")
public class Recipe {

	@Id
	@Field("_id")
    private String id;
    
	@Field("rcp_id")
	private Integer rcpId; // 자바에서 camelCase로 사용
    
    @Field("rcp_name") 
    private String rcpName;
    
    @Field("rcp_category") // MongoDB에서는 스네이크 케이스로 사용
    private String rcpCategory;
    
    @Field("rcp_expain") // MongoDB에서는 스네이크 케이스로 사용
    private String rcpExpain;
    
    @Field("rcp_mainphoto") // MongoDB에서는 스네이크 케이스로 사용
    private String rcpMainphoto;
    
    @Field("rcp_rec") // MongoDB에서는 스네이크 케이스로 사용
    private Integer rcpRec;
    
    private List<SubRecipe> subRecipes;
}
