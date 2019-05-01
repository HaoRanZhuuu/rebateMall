package com.zhuhaoran.rebatemall.viewObject;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author ZhuHaoran
 * @className ProductInfoVo
 * @date 2019/4/10
 * @description
 */

@Data
public class ProductInfoVo {

    private String productId;

    private String productName;

    private BigDecimal productPrice;

    private BigDecimal productDiscount;

    private String productIcon;
}
