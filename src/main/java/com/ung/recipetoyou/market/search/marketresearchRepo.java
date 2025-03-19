package com.ung.recipetoyou.market.search;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import jakarta.transaction.Transactional;

@Repository
public interface marketresearchRepo extends CrudRepository<marketresultBeans, String> {
	@Modifying
	@Transactional
	@Query(value = "UPDATE rty_product_AnalDTA " + "SET " + "rPA_schkey = :keyInput, " + "rPA_title = :TitleInput, "
			+ "rPA_price = :PriceInput,  " + "rPA_img = :imgInput, " + "rPA_link = :linkInput "
			+ "WHERE rPA_prodid = :InputParam", nativeQuery = true)

	void updaterecord(@Param("keyInput") String resultdata, @Param("TitleInput") String ttitle,
			@Param("PriceInput") Integer pprice, @Param("imgInput") String iimg, @Param("linkInput") String llnk,
			@Param("InputParam") String pprodid);

	@Query(value = "SELECT rPA_title, AVG(rPA_price), MIN(rPA_price),  PERCENTILE_CONT(0.85) WITHIN GROUP (ORDER BY rPA_price),  ROUND(STDDEV(rPA_price), 2), COUNT(*) "
			+ "FROM rty_product_AnalDTA " + "GROUP BY rPA_title, rPA_schkey = :userInput", nativeQuery = true)
	List<Object[]> AnalDTASELECTER2(@Param("userInput") String input);

//	@Query(value = "SELECT rPA_schkey, AVG(rPA_price), MIN(rPA_price),  PERCENTILE_CONT(0.85) WITHIN GROUP (ORDER BY rPA_price),  ROUND(STDDEV(rPA_price), 2), COUNT(*) "
//			     + "FROM rty_product_AnalDTA "
//			     + "WHERE rPA_schkey = :userInput "
//			     + "GROUP BY rpa_schkey"
//			     ,nativeQuery = true)
//	List<Object[]> AnalDTASELECTER(@Param("userInput")String input);

//	@Query(value = "SELECT rPA_schkey, rPA_title, AVG(rPA_price) AS 평균가격, MIN(rPA_price) AS 최소가격, " +
//            "PERCENTILE_CONT(0.85) WITHIN GROUP (ORDER BY rPA_price) AS 백분위가격, " +
//            "ROUND(STDDEV(rPA_price), 2) AS 표준편차, COUNT(*) AS 데이터수 " +
//            "FROM rty_product_AnalDTA " +
//            "WHERE rPA_title LIKE %:userInput% " +
//            "GROUP BY rPA_schkey, rPA_title", 
//    nativeQuery = true)
//	List<Object[]> AnalDTASELECTER(@Param("userInput") String input);
	@Query(value = "SELECT AVG(rPA_price) AS 평균가격, " +
            "PERCENTILE_CONT(0.15) WITHIN GROUP (ORDER BY rPA_price) AS 백분위_15, " +
            "PERCENTILE_CONT(0.85) WITHIN GROUP (ORDER BY rPA_price) AS 백분위_85, " +
            "ROUND(STDDEV(rPA_price), 2) AS 표준편차, COUNT(*) AS 데이터수 " +
            "FROM rty_product_AnalDTA " +
            "WHERE rPA_title LIKE %:userInput% ", 
    nativeQuery = true)
List<Object[]> findStatisticsWithPercentiles(@Param("userInput") String input);


	@Query(value = "SELECT rPA_schkey, rPA_title, AVG(rPA_price) AS 평균가격, MIN(rPA_price) AS 최소가격, "
			+ "MAX(rPA_price) AS 최대가격, ROUND(STDDEV(rPA_price), 2) AS 표준편차, COUNT(*) AS 데이터수 "
			+ "FROM rty_product_AnalDTA " + "WHERE rPA_title LIKE %:userInput% " + "GROUP BY rPA_schkey, rPA_title "
			+ "ORDER BY rPA_schkey", nativeQuery = true)
	List<Object[]> AnalDTASELECTER(@Param("userInput") String input);

	@Query(value = "SELECT * " + "FROM( "
			+ "SELECT rpA_title ,rPA_img , rPA_link ,PERCENTILE_CONT(0.15) WITHIN GROUP (ORDER BY rPA_price) AS lwp "
			+ "FROM rty_product_AnalDTA " + "WHERE rPA_schkey = :cheapInput " + "GROUP BY rPA_title, rPA_img, rPA_link "
			+ ") " + "WHERE rownum <=10", nativeQuery = true)
	List<Object[]> cheapertensearch(@Param("cheapInput") String inputc);
}
