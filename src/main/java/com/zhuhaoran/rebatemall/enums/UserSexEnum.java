package com.zhuhaoran.rebatemall.enums;

import lombok.Getter;

/**
 * @author ZhuHaoran
 * @enumName UserSexEnum
 * @date 2019/4/12
 * @description
 */

@Getter
public enum UserSexEnum {

    SECRET (0,"保密"),
    MALE (1,"男性"),
    FEMALE (2,"女性")
    ;

    private Integer code;

    private String message;

    UserSexEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
