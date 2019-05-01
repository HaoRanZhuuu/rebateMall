package com.zhuhaoran.rebatemall.viewObject;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author ZhuHaoran
 * @className OrderDetailVo
 * @date 2019/4/27
 * @description
 */
@Data
public class OrderDetailVo {

    private String productId;

    private String productName;

    private BigDecimal productPrice;

    private BigDecimal productDiscount;

    private Integer productQuantity;

    private String productIcon;
}
