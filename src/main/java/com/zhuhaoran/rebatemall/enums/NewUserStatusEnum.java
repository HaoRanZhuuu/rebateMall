package com.zhuhaoran.rebatemall.enums;

import lombok.Getter;

/**
 * @author ZhuHaoran
 * @enumName NewUserStatusEnum
 * @date 2019/4/12
 * @description
 */

@Getter
public enum NewUserStatusEnum {

    IS_NEW (0,"新用户"),
    NOT_NEW (1,"老用户")
    ;

    private Integer code;

    private String message;

    NewUserStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
