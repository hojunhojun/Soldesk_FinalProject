package com.ung.recipetoyou.recipe;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class MainPageRecipes {
	private List<Recipe> recipes;
}
