package com.ung.recipetoyou.recipe;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRecipeRepo extends JpaRepository<MemberRecipe, Integer>{
	Optional<MemberRecipe> findOptionalByMemberIdAndRecipeId(String memberId, Integer recipeId);
}
