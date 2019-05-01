package com.zhuhaoran.rebatemall.enums;

import lombok.Getter;

/**
 * @author ZhuHaoran
 * @enumName ResultEnum
 * @date 2019/4/15
 * @description
 */

@Getter
public enum ResultEnum {
    VARIABLE_ERROR(0, "参数错误"),
    USERNAME_OR_PASSWORD_ERROE(1,"用户名或密码不正确"),
    REPEAT_USERNAME(2,"用户名已被注册"),
    USER_NOT_EXIST(3,"用户不存在"),
    KEY_WORD_IS_EMPTY(4,"关键词为空"),
    PRODUCT_NOT_EXIST(5,"商品不存在"),
    CATEGORY_NOT_EXIST(6,"类目为空"),
    PRODUCT_STOCK_ERROR(7,"库存错误"),
    CART_EMPTY(8,"购物车为空"),
    ADDRESS_NOT_EXIST(9,"收货地址不存在"),
    UNKNOWN_ERROR(999,"未知错误"),

    ;


    private Integer code;

    private String message;

    ResultEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }}
