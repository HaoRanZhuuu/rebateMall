package com.zhuhaoran.rebatemall.enums;

import lombok.Getter;

/**
 * @author ZhuHaoran
 * @className ShippingStatusEnum
 * @date 2019/4/20
 * @description
 */

@Getter
public enum  ShippingStatusEnum {
    WAIT(0,"等待发货"),
    SHIPPING(1,"在途中")
    ;

    private Integer code;

    private String message;

    ShippingStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }}
