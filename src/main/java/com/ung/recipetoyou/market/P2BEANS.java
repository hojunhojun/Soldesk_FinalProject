package com.ung.recipetoyou.market;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity(name = "PRICE_HISTORY")
public class P2BEANS {
	
	@Id
    @Column(name = "PRODUCT_ID", nullable = false)
    private String productId;
	
    @Column(name = "PRICE_DATE", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date priceDate;

    @Column(name = "LPRICE", nullable = false)
    private Double lprice;

    @Column(name = "HPRICE")
    private Double hprice;
    
    @Column(name = "AVG_PRICE")
    private Double avgprice;
    
    @Column(name = "CATEGORY2")
    private String category2;

    @Column(name = "CATEGORY3")
    private String category3;

    @Column(name = "CREATED_AT")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
}
