package com.zhuhaoran.rebatemall.viewObject;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author ZhuHaoran
 * @className DebateProductDetailVo
 * @date 2019/4/25
 * @description
 */

@Data
public class DebateProductDetailVo {

    private String productId;

    private String productName;

    private BigDecimal productPrice;

    private BigDecimal productDiscount;

    private Integer productStock;

    private String productDescription;

    private String productIcon;

    private Integer categoryId;

    private String sellerId;
}
