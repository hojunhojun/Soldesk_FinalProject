package com.ung.recipetoyou.market;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SearchDTApriceRepo extends CrudRepository<searchDTApriceBeans, Integer> {

	@Query(value = "SELECT AVG(rPA_price) AS 평균가격, " + "MIN(rPA_price) AS 최소가격, "
			+ "PERCENTILE_CONT(0.85) WITHIN GROUP (ORDER BY rPA_price) AS 최대가격, "
			+ "ROUND(STDDEV(rPA_price), 2) AS 표준편차, " + "COUNT(*) AS 데이터수 " + "FROM rty_product_AnalDTA "
			+ "WHERE rpA_schkey = :userInputKeyword", nativeQuery = true)
	List<Object[]> PriceDTaComportprice(@Param("userInputKeyword") String inputkey);

	@Query(value = "SELECT " + "TRUNC(rMKP_DATE) AS 날짜, "
			+ "PERCENTILE_CONT(0.50) WITHIN GROUP (ORDER BY rMKP_aver) AS 중간가격, "
			+ "PERCENTILE_CONT(0.15) WITHIN GROUP (ORDER BY rMKP_low) AS 저가, "
			+ "PERCENTILE_CONT(0.90) WITHIN GROUP (ORDER BY rMKP_high) AS 고가, " + "AVG(rMKP_pyun) AS 표준편차, "
			+ "SUM(rMKP_DTACT) AS 데이터수 " + "FROM rty_Market_Keyword_Price "
			+ "WHERE rMKP_schkey LIKE '%' || :keyword || '%' " + "GROUP BY TRUNC(rMKP_DATE) "
			+ "ORDER BY TRUNC(rMKP_DATE)", nativeQuery = true)
	List<Object[]> PriceDTaComport(@Param("keyword") String keyword);

	@Query(value = "SELECT DISTINCT rMKP_title from rty_market_keyword_price", nativeQuery = true)
	List<String> findDistinctRmkpschy();
}
