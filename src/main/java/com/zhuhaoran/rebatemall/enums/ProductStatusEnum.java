package com.zhuhaoran.rebatemall.enums;

import lombok.Getter;

/**
 * @author ZhuHaoran
 * @enumName ProductStatus
 * @date 2019/4/8
 * @description
 */
@Getter
public enum  ProductStatusEnum {

    UP(0,"在售"),
    DOWN(1,"下架")
    ;

    private Integer code;

    private String message;

    ProductStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
