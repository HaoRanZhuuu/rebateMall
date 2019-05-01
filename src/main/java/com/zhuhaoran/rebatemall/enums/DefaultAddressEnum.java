package com.zhuhaoran.rebatemall.enums;

import lombok.Getter;

/**
 * @author ZhuHaoran
 * @className DefaultAddressEnum
 * @date 2019/4/22
 * @description
 */

@Getter
public enum  DefaultAddressEnum {

    NOT_DEFAULT(0,"非默认地址"),
    IS_DEFAULT(1,"默认地址")
    ;

    private Integer code;

    private String message;

    DefaultAddressEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }}
