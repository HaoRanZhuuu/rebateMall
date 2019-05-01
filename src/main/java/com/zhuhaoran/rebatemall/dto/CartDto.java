package com.zhuhaoran.rebatemall.dto;

import lombok.Data;

/**
 * @author ZhuHaoran
 * @className CartDto
 * @date 2019/4/21
 * @description
 */
@Data
public class CartDto {

    private String productId;

    private Integer productQuantity;

    public CartDto(String productId, Integer productQuantity) {
        this.productId = productId;
        this.productQuantity = productQuantity;
    }
}
