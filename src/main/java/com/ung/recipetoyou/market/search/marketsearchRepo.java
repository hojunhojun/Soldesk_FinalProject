package com.ung.recipetoyou.market.search;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface marketsearchRepo extends CrudRepository<marketsearchBeans, Integer> {
	@Query(value = "select * " + "FROM( " + "SELECT rtr_keyword, SUM(rtr_search_count) as tsc "
			+ "FROM rty_search_rank " + "GROUP BY rtr_keyword " + "ORDER BY tsc DESC) "
			+ "WHERE ROWNUM <= 10", nativeQuery = true)
	List<Object[]> SQLSELECTER2();

	public abstract List<marketsearchBeans> findAll();
}
