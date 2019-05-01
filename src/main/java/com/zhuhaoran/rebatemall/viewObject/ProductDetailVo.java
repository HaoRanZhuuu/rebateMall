package com.zhuhaoran.rebatemall.viewObject;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author ZhuHaoran
 * @className ProductDetailVo
 * @date 2019/4/19
 * @description
 */

@Data
public class ProductDetailVo {

    private String productId;

    private String productName;

    private BigDecimal productPrice;

    private BigDecimal productDiscount;

    private Integer productStock;

    private String productDescription;

    private String productIcon;

    private Integer categoryId;

}
