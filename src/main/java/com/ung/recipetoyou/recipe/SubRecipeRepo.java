package com.ung.recipetoyou.recipe;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubRecipeRepo extends MongoRepository<SubRecipe, String> {
	public abstract List<SubRecipe> findAll();
	public abstract List<SubRecipe> findByRcpId(Integer rcpId);
	public abstract List<SubRecipe> findByRcpNameContaining(String rcpName);
	public abstract SubRecipe findByRcpRec(Integer rcpRec);
	public abstract SubRecipe findByRcpIdAndRcpName(Integer rcpId, String rcpName);
}
