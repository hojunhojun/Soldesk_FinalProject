package com.ung.recipetoyou.community;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class FoodGuideCommuPosts {
	private List<CommunityPost> foodguideCommuList;
	private Integer pageCount;
}
