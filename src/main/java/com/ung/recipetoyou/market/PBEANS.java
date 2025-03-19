package com.ung.recipetoyou.market;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity(name = "PRODUCT")
public class PBEANS {

    @Id
    @Column(name = "PRODUCT_ID", length = 50, nullable = false)
    private String productId;
    
    @Column(name = "PRICE", length = 200, nullable = false)
    private int price;
    
    @Column(name = "TITLE", length = 200, nullable = false)
    private String title;

    @Column(name = "LINK", length = 500)
    private String link;

    @Column(name = "IMAGE", length = 500)
    private String image;

    @Column(name = "MALL_NAME", length = 100)
    private String mallName;

    @Column(name = "BRAND", length = 100)
    private String brand;

    @Column(name = "MAKER", length = 100)
    private String maker;

    @Column(name = "CATEGORY1", length = 100)
    private String category1;

    @Column(name = "CATEGORY2", length = 100)
    private String category2;

    @Column(name = "CATEGORY3", length = 100)
    private String category3;

    @Column(name = "CATEGORY4", length = 100)
    private String category4;

    @Column(name = "PRODUCT_TYPE")
    private Integer productType;

    @Column(name = "REGISTERED_DATE")
    @Temporal(TemporalType.DATE)
    private Date registeredDate;
}
