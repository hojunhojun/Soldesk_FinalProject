package com.ung.recipetoyou.market;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import jakarta.transaction.Transactional;

public interface PRepo extends CrudRepository<PBEANS, String> {
	
	@Transactional
	@Modifying
	@Query(value = """
	    INSERT INTO PRICE_HISTORY (PRODUCT_ID, PRICE_DATE, LPRICE, HPRICE, AVG_PRICE, CATEGORY2, CATEGORY3, CREATED_AT)
	    SELECT
	        MIN(PRODUCT_ID) AS PRODUCT_ID,
	        SYSDATE AS PRICE_DATE,
	        MIN(PRICE) AS LPRICE,
	        MAX(PRICE) AS HPRICE,
	        AVG(PRICE) AS AVG_PRICE,
	        CATEGORY2,
	        CATEGORY3,
	        SYSDATE AS CREATED_AT
	    FROM PRODUCT
	    WHERE PRICE IS NOT NULL
	    GROUP BY CATEGORY2, CATEGORY3
	    """, nativeQuery = true)
	void insertCurrentPriceHistory();


}
