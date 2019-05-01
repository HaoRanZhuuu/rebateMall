package com.zhuhaoran.rebatemall.form;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * @author ZhuHaoran
 * @className ProductForm
 * @date 2019/4/30
 * @description
 */

@Data
public class ProductForm {

    @NotEmpty(message = "商品名不能为空")
    private String productName;

    @NotEmpty(message = "不能为空")
    private String productPrice;

    @NotEmpty(message = "不能为空")
    private String productDiscount;

    @NotEmpty(message = "不能为空")
    private String productStock;

    @NotEmpty(message = "不能为空")
    private String productDescription;

    @NotEmpty(message = "不能为空")
    private String productIcon;

    @NotEmpty(message = "不能为空")
    private String productStatus;

    @NotEmpty(message = "不能为空")
    private String categoryId;
}
