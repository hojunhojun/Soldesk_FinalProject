package com.ung.recipetoyou.recipe;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface MainRecipeRepo extends MongoRepository<Recipe, String> {
    public abstract List<Recipe> findAll();
    public abstract Recipe findByRcpId(Integer rcpId);
    public abstract List<Recipe> findByRcpNameContaining(String rcpName);
    public abstract long countByRcpNameContaining(String rcpNameCount);
    public abstract List<Recipe> findByRcpNameContaining(String rcpNameCount, Pageable p);
    public abstract void save(String string);
    public abstract List<Recipe> findById(Integer no);
    public abstract long countByRcpCategoryContaining(String rcpCategoryCount);
    public abstract long countByRcpNameContainingAndRcpCategory(String rcpName, String rcpCategory);
    public abstract List<Recipe> findByRcpNameContainingAndRcpCategory(String rcpName, String rcpCategory, Pageable pageable);
    public abstract List<Recipe> findByRcpNameContainingIgnoreCase(String rcpName, Pageable pageable);
    public abstract long countByRcpNameContainingIgnoreCase(String rcpName);
    public abstract List<Recipe> findByRcpNameContainingIgnoreCaseAndRcpCategoryContainingIgnoreCase(String rcpName, String rcpCategory, Pageable pageable);
    public abstract long countByRcpNameContainingIgnoreCaseAndRcpCategoryContainingIgnoreCase(String rcpName, String rcpCategory);
}