package com.ung.recipetoyou.arcade;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface QuizRepository extends CrudRepository<Quiz, Integer>{
	public abstract List<Quiz> findByType(String s);
}
