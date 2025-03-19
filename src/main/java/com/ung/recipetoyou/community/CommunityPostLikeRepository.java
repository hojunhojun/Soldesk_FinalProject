package com.ung.recipetoyou.community;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ung.recipetoyou.recipe.Recipe;

@Repository
public interface CommunityPostLikeRepository extends CrudRepository<CommunityPostLike, Integer> {

	public abstract CommunityPostLike findByMemIdAndPostNo(String id, Integer no);
	public abstract List<CommunityPostLike> findAll();
	public abstract CommunityPost findByNo(Integer int1);
	public abstract CrudRepository<Recipe, String> findAllByPostNo(int int1);
	public abstract boolean existsByPostNoAndMemIdAndPostType(int int1, String id, Integer pt);
}
