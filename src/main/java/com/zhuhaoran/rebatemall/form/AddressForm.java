package com.zhuhaoran.rebatemall.form;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * @author ZhuHaoran
 * @className AddressForm
 * @date 2019/4/22
 * @description
 */

@Data
public class AddressForm {

    /**id*/
    private String addressId;

    /**用户id*/
    @NotEmpty(message = "用户id不能为空")
    private String userId;

    /**收货人名称*/
    @NotEmpty(message = "收货人姓名不能为空")
    private String deliveryName;

    /**收货人手机*/
    @NotEmpty(message = "收货人手机不能为空")
    private String deliveryPhone;

    /**收货人地址*/
    @NotEmpty(message = "收货人地址不能为空")
    private String deliveryAddress;

    /**是否默认地址*/
    private Integer isDefault;

}
