package com.ung.recipetoyou.market;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import jakarta.transaction.Transactional;

public interface P2Repo extends CrudRepository<P2BEANS, String> {
	 List<P2BEANS> findAll();
}
