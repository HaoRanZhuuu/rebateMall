package com.zhuhaoran.rebatemall.dataobject;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;

/**
 * @author ZhuHaoran
 * @className ProductInfo
 * @date 2019/4/7
 * @description
 */

@Entity
@Data
public class ProductInfo {

    @Id
    /* 商品id. */
    private String productId;

    /* 商品名称. */
    private String productName;

    /* 商品售价. */
    private BigDecimal productPrice;

    /* 商品优惠价格. */
    private BigDecimal productDiscount;

    /* 剩余库存. */
    private Integer productStock;

    /* 商品描述.*/
    private String productDescription;

    /* 商品缩略图. */
    private String productIcon;

    /* 商品状态. */
    private Integer productStatus;

    /* 类目id. */
    private Integer categoryId;

    public ProductInfo() {
    }

    public ProductInfo(String productId, String productName, BigDecimal productPrice, BigDecimal productDiscount, Integer productStock, String productDescription, String productIcon, Integer productStatus, Integer categoryId) {
        this.productId = productId;
        this.productName = productName;
        this.productPrice = productPrice;
        this.productDiscount = productDiscount;
        this.productStock = productStock;
        this.productDescription = productDescription;
        this.productIcon = productIcon;
        this.productStatus = productStatus;
        this.categoryId = categoryId;
    }
}
