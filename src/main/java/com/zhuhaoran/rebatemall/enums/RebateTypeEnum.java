package com.zhuhaoran.rebatemall.enums;

import lombok.Getter;

/**
 * @author ZhuHaoran
 * @enumName RebateTypeEnum
 * @date 2019/4/20
 * @description
 */
@Getter
public enum RebateTypeEnum {

    INVITE_NEW(0, "邀请新用户"),
    SELL_PRODUCT(1,"推销商品")
    ;

    private Integer code;

    private String message;

    RebateTypeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }}
