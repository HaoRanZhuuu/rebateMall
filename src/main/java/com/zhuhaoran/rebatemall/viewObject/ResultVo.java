package com.zhuhaoran.rebatemall.viewObject;

import lombok.Data;

/**
 * @author ZhuHaoran
 * @className ResultVo
 * @date 2019/4/10
 * @description Http请求返回的外层对象
 */

@Data
public class ResultVo<T> {

    /** 错误码. */
    private Integer code;

    /**提示信息. */
    private String msg;

    /**返回内容. */
    private T data;
}
