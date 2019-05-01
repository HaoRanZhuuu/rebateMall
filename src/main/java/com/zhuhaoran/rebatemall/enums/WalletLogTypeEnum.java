package com.zhuhaoran.rebatemall.enums;

import lombok.Getter;

/**
 * @author ZhuHaoran
 * @enumName WalletLogTypeEnum
 * @date 2019/4/20
 * @description
 */
@Getter
public enum WalletLogTypeEnum {

    INCOME(0, "收入"),
    EXPEND(1,"支出")
    ;

    private Integer code;

    private String message;

    WalletLogTypeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }}
