package com.zhuhaoran.rebatemall.enums;

import lombok.Getter;

/**
 * @author ZhuHaoran
 * @enumName OrderStatusEnum
 * @date 2019/4/20
 * @description
 */

@Getter
public enum OrderStatusEnum {

    NEW(0,"新订单"),
    FINISH(1,"订单完成"),
    CANCEL(2,"订单取消")
    ;

    private Integer code;

    private String message;

    OrderStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }}
