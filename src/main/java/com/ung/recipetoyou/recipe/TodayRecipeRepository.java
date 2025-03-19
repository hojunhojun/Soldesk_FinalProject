package com.ung.recipetoyou.recipe;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TodayRecipeRepository extends CrudRepository<TodayRecipe, Integer> {

	public abstract TodayRecipe findFirstByOrderByNameAsc();

}