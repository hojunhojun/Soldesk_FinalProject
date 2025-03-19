package com.ung.recipetoyou.recipe;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Document(collection = "recipe_sub_db")
public class SubRecipe {
    
	@Id
	@Field("_id")
	private String id;
    
	@Field("rcp_id")
	private Integer rcpId;
    	
    private ObjectId Id; // 자바에서는 camelCase로 사용하고, MongoDB에서는 스네이크 케이스로 매핑
    @Field("rcp_name")
    private String rcpName; // MongoDB에서는 스네이크 케이스로 사용
    
    @Field("rcp_category")
    private String rcpCategory; // MongoDB에서는 스네이크 케이스로 사용
    
    @Field("rcp_mainBphoto")
    private String rcpMainBphoto; // MongoDB에서는 스네이크 케이스로 사용
    
    @Field("rcp_rec")
    private Integer rcpRec; // MongoDB에서는 스네이크 케이스로 사용
    
    @Field("rcp_parts")
    private String rcpParts; // MongoDB에서는 스네이크 케이스로 사용
    
    @Field("rcp_if_eng")
    private String rcpIfEng; // MongoDB에서는 스네이크 케이스로 사용
    
    @Field("rcp_if_car")
    private String rcpIfCar; // MongoDB에서는 스네이크 케이스로 사용
    
    @Field("rcp_if_pro")
    private String rcpIfPro; // MongoDB에서는 스네이크 케이스로 사용
    
    @Field("rcp_if_fat")
    private String rcpIfFat; // MongoDB에서는 스네이크 케이스로 사용
    
    @Field("rcp_if_na")
    private String rcpIfNa; // MongoDB에서는 스네이크 케이스로 사용
    
    @Field("rcp_tip")
    private String rcpTip; // MongoDB에서는 스네이크 케이스로 사용

    // 동적 필드들 (어떤 건 04까지만 있고 어떤 건 08까지 있고...)
    @Field("rcp_mul01")
    private String rcpMul01;
    
    @Field("rcp_mul02")
    private String rcpMul02;
    
    @Field("rcp_mul03")
    private String rcpMul03;
    
    @Field("rcp_mul04")
    private String rcpMul04;
    
    @Field("rcp_mul05")
    private String rcpMul05;
    
    @Field("rcp_mul06")
    private String rcpMul06;
    
    @Field("rcp_mul07")
    private String rcpMul07;
    
    @Field("rcp_mul08")
    private String rcpMul08;
    
    @Field("rcp_mul09")
    private String rcpMul09;
    
    @Field("rcp_mul10")
    private String rcpMul10;
    
    @Field("rcp_mul11")
    private String rcpMul11;
    
    @Field("rcp_mul12")
    private String rcpMul12;
    
    @Field("rcp_mul13")
    private String rcpMul13;
    
    @Field("rcp_mul14")
    private String rcpMul14;
    
    @Field("rcp_mul15")
    private String rcpMul15;
    
    @Field("rcp_mul16")
    private String rcpMul16;
    
    @Field("rcp_mul17")
    private String rcpMul17;
    
    @Field("rcp_mul18")
    private String rcpMul18;
    
    @Field("rcp_mul19")
    private String rcpMul19;
    
    @Field("rcp_mul20")
    private String rcpMul20;
    
    @Field("rcp_subimg01")
    private String rcpSubimg01;
    
    @Field("rcp_subimg02")
    private String rcpSubimg02;
    
    @Field("rcp_subimg03")
    private String rcpSubimg03;
    
    @Field("rcp_subimg04")
    private String rcpSubimg04;
    
    @Field("rcp_subimg05")
    private String rcpSubimg05;
    
    @Field("rcp_subimg06")
    private String rcpSubimg06;
    
    @Field("rcp_subimg07")
    private String rcpSubimg07;
    
    @Field("rcp_subimg08")
    private String rcpSubimg08;
    
    @Field("rcp_subimg09")
    private String rcpSubimg09;
    
    @Field("rcp_subimg10")
    private String rcpSubimg10;
    
    @Field("rcp_subimg11")
    private String rcpSubimg11;
    
    @Field("rcp_subimg12")
    private String rcpSubimg12;
    
    @Field("rcp_subimg13")
    private String rcpSubimg13;
    
    @Field("rcp_subimg14")
    private String rcpSubimg14;
    
    @Field("rcp_subimg15")
    private String rcpSubimg15;
    
    @Field("rcp_subimg16")
    private String rcpSubimg16;
    
    @Field("rcp_subimg17")
    private String rcpSubimg17;
    
    @Field("rcp_subimg18")
    private String rcpSubimg18;
    
    @Field("rcp_subimg19")
    private String rcpSubimg19;
    
    @Field("rcp_subimg20")
    private String rcpSubimg20;
}
