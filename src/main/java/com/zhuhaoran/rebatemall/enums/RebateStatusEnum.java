package com.zhuhaoran.rebatemall.enums;

import lombok.Getter;

/**
 * @author ZhuHaoran
 * @enumName RebateStatusEnum
 * @date 2019/4/20
 * @description
 */

@Getter
public enum RebateStatusEnum {

    WAIT(0, "未完成"),
    FINISH(1,"完成")
    ;

    private Integer code;

    private String message;

    RebateStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }}
