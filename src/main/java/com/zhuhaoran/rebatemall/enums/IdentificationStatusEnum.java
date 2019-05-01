package com.zhuhaoran.rebatemall.enums;

import lombok.Getter;

/**
 * @author ZhuHaoran
 * @enumName IdentificationStatusEnum
 * @date 2019/4/12
 * @description
 */

@Getter
public enum IdentificationStatusEnum {

    NOT_IDENTIFICATION (0,"非认证用户"),
    IS_IDENTIFICATION (1,"认证用户")
    ;

    private Integer code;

    private String message;

    IdentificationStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
